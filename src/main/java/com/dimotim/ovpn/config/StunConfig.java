package com.dimotim.ovpn.config;

import lombok.Value;

@Value
public class StunConfig {
    String stunHost;
    int stunPort;
}
