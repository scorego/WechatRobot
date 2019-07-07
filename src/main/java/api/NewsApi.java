package api;

import config.RedisConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import robot.RollToolsApi.RollNews;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 16:01
 */
@Slf4j
public class NewsApi {


    // 510 代表科技新闻
    private static final String DEFAULT_NEWS_TYPE_ID = "510";

    public static String dealNewsMsg(WXMessage message) {
        if (!RedisConfig.isRedisEnable()) {
            return null;
        }
        String keyword = message.content.trim();
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return getTodayNews(keyword);
    }

    public static String getTodayNews(@NonNull String keyword) {
        if (!RedisConfig.isRedisEnable()) {
            return null;
        }
        return ("新闻".equals(keyword)) ? getTodayTotalNews() : null;
    }

    private static String getTodayTotalNews() {
        return RollNews.getTodayTotalNews(DEFAULT_NEWS_TYPE_ID, 10);
    }

//    private static String getToadyNewsByIndex(String newsIndex) {
//        return RollNews.getToadyNewsByIndex(DEFAULT_NEWS_TYPE_ID, newsIndex);
//    }

}
