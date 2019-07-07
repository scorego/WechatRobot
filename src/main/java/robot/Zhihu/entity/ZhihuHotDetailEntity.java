package robot.Zhihu.entity;

import cons.WxMsg;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 19:34
 */
@Data
public class ZhihuHotDetailEntity {

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String thumbnail;
    private String ga_prefix;
    private long id;

    public String getResult() {
        return "【知乎热榜】" + title + WxMsg.LINE
                + "【查看详情】" + share_url + WxMsg.LINE;
    }

}
