package robot.Zhihu.entity;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 19:27
 */
@Data
public class ZhihuHotContentEntity {
    private long news_id;

    private String url;

    private String thumbnail;

    private String title;

}

