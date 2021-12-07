package com.javafx.demo.utils;

import java.util.ResourceBundle;

/**
 * @author
 * @date
 */
public class ResourceBundleUtil {

    private static final ResourceBundle RESOURCE;

    static {
        RESOURCE = ResourceBundle.getBundle("i18n.message", ConfigUtil.defaultLocale);
    }

    private ResourceBundleUtil() {
    }

    public static ResourceBundle getResource() {
        return RESOURCE;
    }

    public static String getStringValue(String key) {
        try {
            return RESOURCE.getString(key);
        } catch (Exception exception) {
            return "";
        }
    }

}