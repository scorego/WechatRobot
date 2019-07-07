package api;


import config.RedisConfig;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import robot.Zhihu.ZhihuHot;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 18:57
 */
public class ZhihuHotApi {


    public static String dealZhihuHotMsg(WXMessage message) {
        if (RedisConfig.isRedisNotEnable()) {
            return null;
        }
        String keyword = message.content.trim();
        if ("知乎".equals(keyword)) {
            return ZhihuHot.getZhihuHot();
        }
        int index = 0;
        try {
            index = Integer.valueOf(keyword.substring(2).trim());
        } catch (NumberFormatException e) {
            return "输入序列号错误。";
        }
        return ZhihuHot.getSingleZhihuHot(index - 1);
    }

}
