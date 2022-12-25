package com.dimotim;

import com.dimotim.ovpn.service.Looper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) {
        Looper.restartLoop();
    }
}
