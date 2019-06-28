package main.facade;

import main.service.everydayHelloMsg.SendEverydayHelloMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/28 14:02
 */
public class SendEverydayMessage {

    private static final Logger log = LoggerFactory.getLogger(SendEverydayMessage.class);

    public static void sendGroupEverydayHello(){
        SendEverydayHelloMsg.SendGroupEveryDayHelloMsg();
    }


}
