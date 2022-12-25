package com.dimotim.ovpn.util;

import com.dimotim.ovpn.config.OpenVpnRunnerConfig;
import org.apache.commons.lang3.SystemUtils;

public class OSDependUtil {
    public static String getOpenVpnPath(OpenVpnRunnerConfig config) {
        if (SystemUtils.IS_OS_WINDOWS) {
            return config.getWindowsPath();
        }
        if (SystemUtils.IS_OS_LINUX) {
            return config.getLinuxPath();
        }
        throw new IllegalStateException();
    }
}
