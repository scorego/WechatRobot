package robot.RollToolsApi;

import cache.redis.NewsCacheFactory;
import cache.redis.entity.NewsCacheEntity;
import com.alibaba.fastjson.JSONObject;
import config.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import robot.RollToolsApi.entity.RollNewsContentEntity;
import robot.RollToolsApi.entity.RollNewsEntity;
import utils.HttpRequestUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 16:10
 */
@Slf4j
public class RollNews {

    private static final String NEWS_LIST_LINK = GlobalConfig.getValue("RollToolsApi.host", "") + GlobalConfig.getValue("RollToolsApi.news.list", "");

//    private static final String NEWS_DETAILS_LINK = GlobalConfig.getValue("RollToolsApi.host", "") + GlobalConfig.getValue("RollToolsApi.news.details", "");

//    private static Map<String, List<RollNewsContentEntity>> TodayNews = new ConcurrentHashMap<>();

    public static String getTodayTotalNews(String typeId, int limit) {
        NewsCacheEntity newsCacheEntity = NewsCacheFactory.getNewsCacheEntity(typeId,limit);
        String todayNews = newsCacheEntity.get();
        if (todayNews != null) {
            return todayNews;
        }
        String link = NEWS_LIST_LINK + typeId;
        String response = HttpRequestUtil.doGet(link);
        if (StringUtils.isBlank(response)) {
            return null;
        }

        RollNewsEntity rollNewsEntity = JSONObject.parseObject(response, RollNewsEntity.class);
        if (rollNewsEntity.isInValid()) {
            return null;
        }
        String result = rollNewsEntity.getTodayNews(limit);
        newsCacheEntity.setValue(result).save();
//        TodayNews.put(typeId, rollNewsEntity.getData());
        return result;
    }

    // 好像没必要做一个查看新闻详情的功能。只要实现一个每日新闻速览就可以了(只展示梗概)
    // 如果有需求做一个查看新闻详情的功能，可以吧这些注释掉的代码取消注释，然后自己完善一下就可以了

//    public static String getToadyNewsByIndex(String typeId, String newsIndex) {
//        int index = Integer.valueOf(newsIndex) - 1;
//        RollNewsContentEntity rollNewsContentEntity = TodayNews.get(typeId).get(index);
//        String newsId = rollNewsContentEntity.getNewsId();
//        return getNewsById( newsId);
//    }
//
//    private static String getNewsById(String newsId) {
//        NewsCacheEntity singleNewsCacheEntity = NewsCacheFactory.getSingleZhihuHotCacheEntity(newsId);
//        String single = singleNewsCacheEntity.get();
//        if (StringUtils.isNotBlank(single)){
//            return single;
//        }
//        String url = NEWS_DETAILS_LINK + newsId;
//        String response = HttpRequestUtil.doGet(url);
//        if (StringUtils.isBlank(response)) {
//            return null;
//        // TODO
//    }


}
