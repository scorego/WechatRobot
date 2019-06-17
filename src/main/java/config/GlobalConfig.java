package config;

import io.github.biezhi.wechat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class GlobalConfig {

    private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);

    private static Properties pp;

    static {
        pp = new Properties();
        try {
            InputStream fps = GlobalConfig.class.getResourceAsStream("/config.properties");
            pp.load(fps);
            fps.close();
        } catch (Exception e) {
            log.error("读取config.properties文件异常!", e);
        }
    }

    public static String getValue(String key, String defalutValue) {
        String value = pp.getProperty(key);
        if (StringUtils.isEmpty(value)){
            return defalutValue;
        }
        return value;
    }
}

