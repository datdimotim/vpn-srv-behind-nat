package com.dimotim.ovpn.service;

import com.dimotim.ovpn.config.TelegramConfig;
import com.dimotim.ovpn.config.TelegramConfigToSend;
import com.dimotim.ovpn.model.MappedAddress;
import com.dimotim.ovpn.util.FileUtils;
import kong.unirest.ContentType;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class TelegramSender {
    TelegramConfig config;

    public void send(MappedAddress mappedAddress) throws Exception {
        if (config.isCommonChatEnabled()) {
            log.info("send to common chat");
            String message = mappedAddress.getIp() + ":" + mappedAddress.getPort();
            sendMessage(config.getBotToken(), config.getCommonChatId(), message);
        } else {
            log.info("send to common chat skipped");
        }

        List<TelegramConfigToSend> toSend = config.getConfigsToSend().stream()
                .filter(TelegramConfigToSend::isEnabled)
                .collect(Collectors.toList());

        for(TelegramConfigToSend conf: toSend) {
            try {
                log.info("send config file: {}", conf.getName());
                String abs = FileUtils.resolveAbsPath(conf.getOvpnClientConfigPath());
                String ovpnConfig = FileUtils.readFileAsString(abs);
                String configToSend = "remote " + mappedAddress.getIp() + " " + mappedAddress.getPort() + "\n"
                        + ovpnConfig;

                String confName = conf.getName() + ".ovpn";

                sendDocument(config.getBotToken(), conf.getChatId(), confName, configToSend);
            } catch (Exception e) {
                log.error("error when send file: {}", conf.getName());
                throw e;
            }
        }
    }

    private void sendMessage(String botToken, String chatId, String message) {
        String urlTemplate = "https://api.telegram.org/bot$TG_BOT_TOKEN/sendMessage";
        String url = urlTemplate.replace("$TG_BOT_TOKEN", botToken);

        Unirest.post(url)
                .field("chat_id", chatId)
                .field("text", message)
                .asBytes();
    }

    private void sendDocument(String botToken, String chatId, String fileName, String fileBody) {
        String urlTemplate = "https://api.telegram.org/bot$TG_BOT_TOKEN/sendDocument";
        String url = urlTemplate.replace("$TG_BOT_TOKEN", botToken);

        Unirest.post(url)
                .field("chat_id", chatId)
                .field("document", fileBody.getBytes(StandardCharsets.UTF_8), ContentType.TEXT_PLAIN, fileName)
                .asBytes();
    }
}
