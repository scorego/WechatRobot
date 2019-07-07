package robot.RollToolsApi.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 17:24
 */
@Data
public class RollNewsContentEntity {

    private String title;

    private List<String> imgList;

    private String source;

    private String newsId;

    private String digest;

    private String postTime;

    private List<String> videoList;


}
