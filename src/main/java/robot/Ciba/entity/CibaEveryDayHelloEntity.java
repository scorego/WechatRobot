package robot.Ciba.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 11:36
 */

@Data
public class CibaEveryDayHelloEntity {
    private String sid;
    private String tts;
    private String content;
    private String note;
    private String love;
    private String translation;
    private String picture;
    private String picture2;
    private String caption;
    private String dateline;
    private String s_pv;
    private String sp_pv;
    private List<CibaEveryDayHelloTags> tags;
    private String fenxiang_img;

    public String getSentence() {
        return content + "\n" + note;
    }
}

@Data
class CibaEveryDayHelloTags {
    private String id;
    private String name;
}
