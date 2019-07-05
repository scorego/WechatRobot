package api.entity;

import cons.WxMsg;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import robot.AToolBox.ToolBoxRubbish;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/5 16:00
 */

@Data
public class RubbishToolBoxResponseEntity {

    private String rubbish;

    private String response;

    private String linkRubbishString;

    public RubbishToolBoxResponseEntity(String rubbish) {
        this.rubbish = rubbish;
    }

    public String getResult() {
        if (StringUtils.isBlank(linkRubbishString) || linkRubbishString.equals(ToolBoxRubbish.RUBBISH_LINK_NOT_EXIST)) {
            return "";
        }
        if (linkRubbishString.equals(ToolBoxRubbish.RUBBISH_LINK_NO_RESPONSE)) {
            return "";
        }
        return "【相关词条】" + linkRubbishString.trim() + WxMsg.LINE;
    }
}
