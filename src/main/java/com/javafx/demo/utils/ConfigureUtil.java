
package com.javafx.demo.utils;

import java.io.File;

/**
 * @author
 * @date
 */
public class ConfigureUtil {
    public ConfigureUtil() {
    }

    public static String getConfigurePath() {
        return System.getProperty("user.home") + "/demoSpringboot/";
    }

    public static String getConfigurePath(String fileName) {
        return getConfigurePath() + fileName;
    }

    public static File getConfigureFile(String fileName) {
        return new File(getConfigurePath(fileName));
    }
}
