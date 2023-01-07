package com.dimotim.ovpn.config;

import com.formkiq.graalvm.annotations.Reflectable;
import lombok.Value;

import java.util.List;

@Value
@Reflectable
public class TelegramConfig {
    String botToken;

    boolean commonChatEnabled;
    String commonChatId;

    List<TelegramConfigToSend> configsToSend;

}
