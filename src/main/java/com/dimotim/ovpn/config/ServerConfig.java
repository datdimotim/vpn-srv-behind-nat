package com.dimotim.ovpn.config;

import lombok.Value;

@Value
public class ServerConfig {
    boolean cycledRun;
    int restartTimeoutMillis;

    int localPort;
    StunConfig stunConfig;
    String serverConfigPath;
    String clientConfigPath;

    TelegramConfig telegramConfig;
}
