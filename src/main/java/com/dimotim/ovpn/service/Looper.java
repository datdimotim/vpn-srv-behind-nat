package com.dimotim.ovpn.service;

import com.dimotim.ovpn.config.MainConfig;
import com.dimotim.ovpn.util.GsonFactory;
import com.dimotim.ovpn.util.FileUtils;
import com.dimotim.ovpn.util.Sleep;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Looper {
    public static void restartLoop() {
        log.info("Starting application");

        MainConfig config;
        try {
            config = FileUtils.readFile("config.json", MainConfig.class);
            log.info("config is: {}", GsonFactory.getGson().toJson(config));
        } catch (Exception e) {
            log.error("read config error: {}", e.getMessage(), e);
            return;
        }

        boolean isCycledRun = config.isServerMode() ?
                config.getServerConfig().isCycledRun() : config.getClientConfig().isCycledRun();
        int restartTimeMillis = config.isServerMode() ?
                config.getServerConfig().getRestartTimeoutMillis() : config.getClientConfig().getRestartTimeoutMillis();

        log.info("cycled run: {}", isCycledRun);
        log.info("restart timeout millis: {}", restartTimeMillis);

        while (true) {
            startWorkflowWithErrorHandling(config);
            if (!isCycledRun) {
                break;
            }
            log.info("wait: {}ms", restartTimeMillis);
            Sleep.sleep(restartTimeMillis);
        }
    }

    public static void startWorkflowWithErrorHandling(MainConfig config) {
        try {
            if (config.isServerMode()) {
                log.info("Starting server workflow");
                ServerWorkflow.startWorkflow(config.getServerConfig(), config.getS3Config(), config.getOpenVpnRunner());
                log.info("Server workflow is finished");
            } else {
                log.info("Starting client workflow");
                ClientWorkflow.startWorkflow(config.getClientConfig(), config.getS3Config(), config.getOpenVpnRunner());
                log.info("Client workflow is finished");
            }
        } catch (Exception e) {
            log.error("workflow finished with error: {}", e.getMessage(), e);
        }
    }
}
