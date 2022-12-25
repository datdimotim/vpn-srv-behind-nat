package com.dimotim.ovpn.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sleep {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.warn("sleep was interrupted: {}", e.getMessage(), e);
        }
    }
}
