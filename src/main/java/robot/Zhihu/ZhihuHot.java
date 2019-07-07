package robot.Zhihu;

import IdentifyCommand.PreProcessMessage;
import cache.redis.ZhihuHotCacheFactory;
import cache.redis.entity.ZhihuHotCacheEntity;
import com.alibaba.fastjson.JSONObject;
import config.GlobalConfig;
import config.RedisConfig;
import cons.WxMsg;
import org.apache.commons.lang3.StringUtils;
import robot.Zhihu.entity.ZhihuHotContentEntity;
import robot.Zhihu.entity.ZhihuHotDetailEntity;
import robot.Zhihu.entity.ZhihuHotEntity;
import utils.HttpRequestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 18:53
 */
public class ZhihuHot {

    private static final String ZHIHU_HOT_LINK = GlobalConfig.getValue("Zhihu.hot", "");

    private static final int ZHIHU_HOT_LIMIT = Integer.valueOf(GlobalConfig.getValue("Zhihu.hot.limit", "9"));

    private static List<ZhihuHotContentEntity> CONTENT = new ArrayList<>(0);

    public static String getZhihuHot() {
        if (RedisConfig.isRedisNotEnable()) {
            return null;
        }
        ZhihuHotCacheEntity zhihuHotCacheEntity = ZhihuHotCacheFactory.getZhihuHotCacheEntity(ZHIHU_HOT_LIMIT);
        String cache = zhihuHotCacheEntity.get();
        if (cache != null) {
            return cache;
        }

        String response = HttpRequestUtil.doGet(ZHIHU_HOT_LINK);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        ZhihuHotEntity zhihuHotEntity = JSONObject.parseObject(response, ZhihuHotEntity.class);

        if (zhihuHotEntity == null) {
            return null;
        }
        CONTENT = zhihuHotEntity.getRecent();
        String content = zhihuHotEntity.getContent(ZHIHU_HOT_LIMIT);
        if (StringUtils.isNotBlank(content)) {
            content = "实时知乎热榜：" + WxMsg.LINE
                    + content
                    + "查看详情请发送>> " + PreProcessMessage.getCommandPrefix() + "知乎" + " + 序号";
        }
        zhihuHotCacheEntity.setValue(content).save();
        return content;
    }

    public static String getSingleZhihuHot(int index) {
        if (RedisConfig.isRedisNotEnable()) {
            return null;
        }
        if (CONTENT == null || CONTENT.size() <= index || index < 0) {
            return null;
        }
        ZhihuHotContentEntity entity = CONTENT.get(index);
        ZhihuHotCacheEntity singleZhihuHotCacheEntity = ZhihuHotCacheFactory.getSingleZhihuHotCacheEntity(entity.getNews_id());
        String result = singleZhihuHotCacheEntity.get();
        if (result != null) {
            return result;
        }
        String url = entity.getUrl();
        String response = HttpRequestUtil.doGet(url);
        if (response == null) {
            return null;
        }
        ZhihuHotDetailEntity zhihuHotDetailEntity = JSONObject.parseObject(response, ZhihuHotDetailEntity.class);
        if (zhihuHotDetailEntity == null || zhihuHotDetailEntity.getShare_url() == null) {
            return null;
        }
        result = zhihuHotDetailEntity.getResult();
        singleZhihuHotCacheEntity.setValue(result).save();
        return result;
    }

}
