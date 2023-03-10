package com.dimotim.ovpn.config;

import com.formkiq.graalvm.annotations.Reflectable;
import lombok.Value;

@Value
@Reflectable
public class ClientConfig {
    boolean cycledRun;
    int restartTimeoutMillis;

    String clientConfigPath;
}
