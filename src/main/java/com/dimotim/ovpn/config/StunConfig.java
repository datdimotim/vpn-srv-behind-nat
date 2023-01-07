package com.dimotim.ovpn.config;

import com.formkiq.graalvm.annotations.Reflectable;
import lombok.Value;

@Value
@Reflectable
public class StunConfig {
    String stunHost;
    int stunPort;
}
