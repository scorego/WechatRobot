package robot.QingyunkeRobot.entity;


import org.apache.commons.lang3.StringUtils;

public class QingyunkeResponseEntity {

    private int code;

    private String content;


    public boolean isValid(){
        return code == 0 && StringUtils.isNotBlank(content);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
