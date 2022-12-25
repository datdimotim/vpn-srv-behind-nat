package com.dimotim.ovpn.config;

import lombok.Value;

@Value
public class MainConfig {
    boolean isServerMode;

    OpenVpnRunnerConfig openVpnRunner;
    S3Config s3Config;

    ServerConfig serverConfig;
    ClientConfig clientConfig;
}