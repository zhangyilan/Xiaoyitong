package ui.test.cn.xiaoyitong.httpHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by asus on 2017/4/13.
 */

public class Httphalper {
    public static  void sendHttpRespose(final String url,final HttpCallback calllisener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url1=new URL(url);
                    conn = (HttpURLConnection) url1.openConnection();
                    conn.setRequestMethod("post");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    InputStream in=conn.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if (calllisener!=null){
                        calllisener.onFinish(response.toString());
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
