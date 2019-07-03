package utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestUtil.class);

    public static String doGet(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        return httpRequest(url);
    }

    private static String httpRequest(String requestUrl) {
        //buffer用于接受返回的字符
        StringBuilder buffer = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            //打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            //获得输入
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //将bufferReader的值给放到buffer里
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            //关闭bufferReader和输入流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (Exception e) {
            log.error("HttpRequestUtil::httpRequest error, requestUrl: {}", requestUrl, e);
        }
        return buffer.toString();
    }
}
