package com.dimotim.ovpn.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
    public static Gson getGson() {
        return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().serializeNulls().create();
    }
}
