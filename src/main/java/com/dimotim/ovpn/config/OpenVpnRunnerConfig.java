package com.dimotim.ovpn.config;

import lombok.Value;

@Value
public class OpenVpnRunnerConfig {
    String linuxPath;
    String windowsPath;
}
