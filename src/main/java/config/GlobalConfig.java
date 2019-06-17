package config;

import io.github.biezhi.wechat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.Properties;

public class GlobalConfig {

    private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);

    private static Properties pp;

    static {
        pp = new Properties();
        try {
            pp.load(new InputStreamReader(GlobalConfig.class.getResourceAsStream("/config.properties"), "GBK"));
        } catch (Exception e) {
            log.error("读取config.properties文件异常!", e);
        }
    }

    public static String getValue(String key, String defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return defaultValue;
        }
        String value = pp.getProperty(key);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }
}

