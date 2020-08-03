package icu.whycode.nettyserver.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class FileTransferProperties {
    private static final Logger logger = LoggerFactory.getLogger(FileTransferProperties.class);

    private static Properties pro = new Properties();

    public static void load(String path) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            pro.load(isr);
        } catch (IOException e) {
            logger.error("读取配置文件异常！！！", e);
        }
    }

    public static String getString(String key, String defaultValue) {
        String value = (String) pro.get(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    public static String getString(String key) {
        String value = (String) pro.get(key);
        return value;
    }

    public static int getInt(String key, int defaultValue) {
        Object value = pro.get(key);
        if (StringUtils.isEmpty(value.toString())) {
            return defaultValue;
        }
        return Integer.parseInt(value.toString());
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        Object value = (Boolean) pro.get(key);
        if (StringUtils.isEmpty(value.toString())) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.toString());
    }
}
