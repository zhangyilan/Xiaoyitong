package ui.test.cn.xiaoyitong.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallback;

/**
 * Created by Administrator on 2017/4/10.
 */

public class HttpUtil {
    private static URL mUrl;
    private static HttpURLConnection mConnection;
    private static BufferedReader mReader = null;


    /**
     * 判断网络是否连接
     *
     * @param context 上下文对象
     * @return 网络是否连接
     */
    public static boolean isNetworkAvailable(Context context) {

        boolean isNetworkOK = false;
        try {
            ConnectivityManager conn = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == conn || null == conn.getActiveNetworkInfo()) {
                isNetworkOK = false;
            } else {
                isNetworkOK = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNetworkOK;
    }


    /**
     * 发送数据
     *
     * @param urlString 网络连接Url
     * @param listener
     * @return 服务器返回的数据
     */
    public static void getData(final String urlString, final HttpCallback listener) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String result = "";
                try {
                    createConnection(urlString);
                    setParams();//post
                    result = readData();
                    if (result != null) {
                        listener.onFinish(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    mConnection.disconnect();

                }

            }

        });
        singleThreadExecutor.shutdown();
    }

    /**
     * 读取数据
     *
     * @throws IOException
     */
    private static String readData() throws IOException {
        String result = null;
        InputStream in = mConnection.getInputStream();
        Scanner scanner = new Scanner(in);
        StringBuffer sb = new StringBuffer();
        while (scanner.hasNext()) {
            sb.append(scanner.next());
        }
        result = sb.toString();
        in.close();
        mConnection.disconnect();
        return result;
    }

    /**
     * 设置网络连接的参数
     */
    private static void setParams() {
        try {
            mConnection.setRequestMethod("GET");
            mConnection.setDoInput(true);
            mConnection.setConnectTimeout(1000 * 8);
            mConnection.setReadTimeout(1000 * 8);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建网络连接
     *
     * @param urlString 网络连接的Url
     * @throws MalformedURLException
     * @throws IOException
     */
    private static void createConnection(String urlString)
            throws MalformedURLException, IOException {
        mUrl = new URL(urlString);
        mConnection = (HttpURLConnection) mUrl.openConnection();
    }

    public static void PostData(String newsurl) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(newsurl)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
