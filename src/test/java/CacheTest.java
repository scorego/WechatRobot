import api.RubbishApi;
import cache.redis.RubbishLinkCacheFactory;
import cache.redis.RubbishTypeCacheFactory;
import cache.redis.entity.RubbishCacheEntity;
import cache.redis.entity.RubbishLinkCacheEntity;
import com.alibaba.fastjson.JSONObject;
import cons.WxMsg;
import enums.RubbishType;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import utils.HttpRequestUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/5 20:15
 */
public class CacheTest {


    public static void main(String[] args) {

        String arr = "鸡蛋#卫生纸#电线#口香糖#报纸#眼镜#车票#汽油#酒";
        RubbishLinkCacheEntity rubbishLinkCache = RubbishLinkCacheFactory.getRubbishLinkCache("");
        RubbishCacheEntity rubbishCacheEntity = RubbishTypeCacheFactory.getRubbishCacheEntity("");
        LinkedList<String> queue = Arrays.stream(arr.split("#")).collect(Collectors.toCollection(LinkedList::new));

        String TOOL_BOX_RUBBISH = "http://www.atoolbox.net/api/GetRefuseClassification.php?key=";
        while (!queue.isEmpty()) {
            String rubbish = queue.pop();
            if (StringUtils.isBlank(rubbish)) {
                continue;
            }
            System.out.println("queue.size = " + queue.size() + " current: " + rubbish);
            rubbishLinkCache.getKeyBuilder().setName(rubbish);

            String s = rubbishLinkCache.get();
            System.out.println("rubbishLink cache: " + s);
            if (s == null) {
                System.out.println(rubbish + " getLinkCache: null");

                String link = TOOL_BOX_RUBBISH;
                try {
                    link += URLEncoder.encode(rubbish, StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    link += rubbish;
                }
                String response = HttpRequestUtil.doGet(link);
                System.out.println("response: " + response);

                if (StringUtils.isBlank(response) || !response.startsWith("{")) {
                    continue;
                }
                Map<String, Map<String, String>> result = JSONObject.parseObject(response, Map.class);
                if (result == null || result.isEmpty()) {
                    continue;
                }
                String resultType = null;
                for (Map.Entry<String, Map<String, String>> mapEntry : result.entrySet()) {
                    queue.offer(mapEntry.getValue().getOrDefault("name", ""));
                    System.out.println("queue offer " + mapEntry.getValue().getOrDefault("name", ""));
                    if (rubbish.equals(mapEntry.getValue().getOrDefault("name", ""))) {
                        resultType = mapEntry.getValue().getOrDefault("type", "");
                        break;
                    }
                }

                RubbishType rubbishType;
                if (resultType == null) {
                    rubbishType = RubbishType.NOT_EXISTS;
                } else {
                    switch (resultType) {
                        case "干垃圾":
                            rubbishType = RubbishType.RESIDUAL_WASTE;
                            break;
                        case "可回收物":
                            rubbishType = RubbishType.RECYCLABLE_WASTE;
                            break;
                        case "湿垃圾":
                            rubbishType = RubbishType.HOUSEHOLD_FOOD_WASTE;
                            break;
                        case "有害垃圾":
                            rubbishType = RubbishType.HAZARDOUS_WASTE;
                            break;
                        default:
                            rubbishType = RubbishType.NOT_EXISTS;
                    }
                }
                rubbishCacheEntity.getKeyBuilder().setName(rubbish);
                rubbishCacheEntity.setValue(rubbishType).save();


                StringBuilder linkResult = new StringBuilder(" ");
                for (Map.Entry<String, Map<String, String>> mapEntry : result.entrySet()) {
                    if (!rubbish.equals(mapEntry.getValue().getOrDefault("name", rubbish))) {
                        linkResult.append(mapEntry.getValue().getOrDefault("name", "")).append(" ");
                    }
                }
                String trim = linkResult.toString().trim();
                String sss = StringUtils.isEmpty(trim) ? "" : trim + WxMsg.LINE;
                rubbishLinkCache.getKeyBuilder().setName(rubbish);
                rubbishLinkCache.setValue(sss).save();


            }

        }


    }

}
