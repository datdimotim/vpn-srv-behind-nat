package com.dimotim.ovpn.config;

import lombok.Value;

import java.util.List;

@Value
public class TelegramConfig {
    String botToken;

    boolean commonChatEnabled;
    String commonChatId;

    List<ConfigToSend> configsToSend;

    @Value
    public static class ConfigToSend {
        String name;
        boolean enabled;
        String ovpnClientConfigPath;
        String chatId;
    }
}
