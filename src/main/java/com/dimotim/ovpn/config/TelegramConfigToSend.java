package com.dimotim.ovpn.config;

import com.formkiq.graalvm.annotations.Reflectable;
import lombok.Value;

@Value
@Reflectable
public class TelegramConfigToSend {
    String name;
    boolean enabled;
    String ovpnClientConfigPath;
    String chatId;
}
