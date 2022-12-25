package com.dimotim.ovpn.service;

import com.dimotim.ovpn.config.ClientConfig;
import com.dimotim.ovpn.config.OpenVpnRunnerConfig;
import com.dimotim.ovpn.config.S3Config;
import com.dimotim.ovpn.model.MappedAddress;
import com.dimotim.ovpn.util.FileUtils;
import com.dimotim.ovpn.util.OSDependUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientWorkflow {

    public static void startWorkflow(ClientConfig clientConfig, S3Config s3Config, OpenVpnRunnerConfig vpnRunnerConfig) throws Exception {
        log.info("Workflow started");

        log.info("read mapped address from s3");
        S3Client s3Client = new S3Client(s3Config);
        MappedAddress mappedAddress = s3Client.readJson(MappedAddress.class);
        log.info("mapped address received from s3: {}", mappedAddress);

        String clientCommandTemplate = "$openvpn --config $clientConfig --remote $mappedIp --port $mappedPort";

        String clientConfigAbsPath = FileUtils.resolveAbsPath(clientConfig.getClientConfigPath());

        String clientCommand = clientCommandTemplate.replace("$clientConfig", clientConfigAbsPath)
                .replace("$openvpn", OSDependUtil.getOpenVpnPath(vpnRunnerConfig))
                .replace("$mappedIp", mappedAddress.getIp())
                .replace("$mappedPort", String.valueOf(mappedAddress.getPort()));

        Runtime runtime = Runtime.getRuntime();

        log.info("starting client with cmd: {}", clientCommand);
        Process clientProcess = runtime.exec(clientCommand);

        int exitCode = clientProcess.waitFor();
        log.warn("client process has finished with code: {}", exitCode);
    }
}
