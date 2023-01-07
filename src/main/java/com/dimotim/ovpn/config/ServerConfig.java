package com.dimotim.ovpn.config;

import com.formkiq.graalvm.annotations.Reflectable;
import lombok.Value;

@Value
@Reflectable
public class ServerConfig {
    boolean cycledRun;
    int restartTimeoutMillis;

    int localPort;
    StunConfig stunConfig;
    String serverConfigPath;
    String clientConfigPath;

    TelegramConfig telegramConfig;
}
