package com.dimotim.ovpn.config;

import lombok.Value;

@Value
public class ClientConfig {
    boolean cycledRun;
    int restartTimeoutMillis;

    String clientConfigPath;
}
