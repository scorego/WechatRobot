package robot.RollToolsApi.entity;

import IdentifyCommand.PreProcessMessage;
import config.GlobalConfig;
import cons.WxMsg;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.swing.border.TitledBorder;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 16:10
 */
@Data
public class RollNewsEntity {

    private static final int DEFAULT_NEWS_SIZE = Integer.valueOf(GlobalConfig.getValue("RollToolsApi.news.list.size", "5"));


    private static final int SUCCESS_CODE = 1;

    private int code;

    private String msg;

    private List<RollNewsContentEntity> data;

    public boolean isInValid() {
        return SUCCESS_CODE != code || data == null || data.isEmpty();
    }

    public String getTodayNews() {
        return getTodayNews(Math.max(DEFAULT_NEWS_SIZE, 0));
    }

    public String getTodayNews(int maxSize) {
        if (isInValid()) {
            return null;
        }
        int length = data.size() > maxSize ? maxSize : data.size();

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            RollNewsContentEntity rollNewsContentEntity = data.get(i);

            result.append(i + 1).append(". ")
                    .append(rollNewsContentEntity.getTitle())
                    .append("(").append(rollNewsContentEntity.getSource()).append(")")
                    .append(WxMsg.LINE);
        }
        return result.toString();
    }

}


