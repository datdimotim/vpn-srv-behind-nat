package com.dimotim.ovpn.config;

import com.formkiq.graalvm.annotations.Reflectable;
import lombok.Value;

@Value
@Reflectable
public class MainConfig {
    boolean isServerMode;

    OpenVpnRunnerConfig openVpnRunner;
    S3Config s3Config;

    ServerConfig serverConfig;
    ClientConfig clientConfig;
}