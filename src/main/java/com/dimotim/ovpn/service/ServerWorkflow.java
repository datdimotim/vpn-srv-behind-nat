package com.dimotim.ovpn.service;

import com.dimotim.ovpn.config.OpenVpnRunnerConfig;
import com.dimotim.ovpn.config.S3Config;
import com.dimotim.ovpn.config.ServerConfig;
import com.dimotim.ovpn.model.MappedAddress;
import com.dimotim.ovpn.util.FileUtils;
import com.dimotim.ovpn.util.OSDependUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerWorkflow {

    public static void startWorkflow(ServerConfig serverConfig, S3Config s3Config, OpenVpnRunnerConfig vpnRunnerConfig) throws Exception {
        log.info("Workflow started");

        StunClient stunClient = new StunClient(serverConfig.getStunConfig());

        log.info("Try to resolve mapped address via stun client");
        MappedAddress mappedAddress = stunClient.resolveMappedAddress(serverConfig.getLocalPort());
        log.info("Mapped address is: {}", mappedAddress);

        String serverCommandTemplate = "$openvpn --config $serverConfig --port $port";

        String serverConfigAbsPath = FileUtils.resolveAbsPath(serverConfig.getServerConfigPath());

        String serverCommand = serverCommandTemplate.replace("$serverConfig", serverConfigAbsPath)
                .replace("$openvpn", OSDependUtil.getOpenVpnPath(vpnRunnerConfig))
                .replace("$port", String.valueOf(serverConfig.getLocalPort()));

        String clientCommandTemplate = "$openvpn --config $clientConfig --remote $mappedIp --port $mappedPort";

        String clientConfigAbsPath = FileUtils.resolveAbsPath(serverConfig.getClientConfigPath());

        String clientCommand = clientCommandTemplate.replace("$clientConfig", clientConfigAbsPath)
                .replace("$openvpn", OSDependUtil.getOpenVpnPath(vpnRunnerConfig))
                .replace("$mappedIp", mappedAddress.getIp())
                .replace("$mappedPort", String.valueOf(mappedAddress.getPort()));

        Runtime runtime = Runtime.getRuntime();

        log.info("starting server with cmd: {}", serverCommand);
        Process serverProcess = runtime.exec(serverCommand);

        log.info("starting client with cmd: {}", clientCommand);
        Process clientProcess = runtime.exec(clientCommand);

        log.info("write mapped address to s3");
        S3Client s3Client = new S3Client(s3Config);
        s3Client.writeJson(mappedAddress);
        log.info("mapped address is written to s3");

        log.info("run telegram sender");
        TelegramSender telegramSender = new TelegramSender(serverConfig.getTelegramConfig());
        telegramSender.send(mappedAddress);
        log.info("telegram sender finished");

        int exitCode = clientProcess.waitFor();
        log.warn("client process has finished with code: {}", exitCode);

        log.info("try to stop server");
        serverProcess.destroy();
        serverProcess.onExit().get();
        log.warn("server process has stopped");
    }
}
