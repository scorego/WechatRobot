package robot.Zhihu.entity;

import cons.WxMsg;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 18:54
 */
@Data
public class ZhihuHotEntity {

    private List<ZhihuHotContentEntity> recent;

    private boolean isValid() {
        return recent != null && !recent.isEmpty();
    }

    public String getContent(int limit) {
        if (!isValid() && limit <= 0) {
            return "";
        }
        int length = Math.min(limit, recent.size());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            ZhihuHotContentEntity zhihuHotContent = recent.get(i);
            sb.append(i + 1).append(". ")
                    .append(zhihuHotContent.getTitle())
                    .append(WxMsg.LINE);
        }
        return sb.toString();
    }
}


