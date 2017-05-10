package ui.test.cn.xiaoyitong.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ui.test.cn.xiaoyitong.httpHelper.HttpCallback;

/**
 * 网络连接工具类
 *
 * @author asus
 */

public class NetUtil {
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
     * @param params    发送的数据
     * @return 服务器返回的数据
     */
    public static void sendData(final String urlString, final String params, final HttpCallback listener) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String result = "";
                try

                {
                    createConnection(urlString);
                    setParams();//post,get
                    writeData(params);
                    result = readData();
                    Log.d("结果是:", result);
                    if (listener != null) {
                        Log.d("结果是:", result);
                        listener.onFinish(result.toString());
                    }
                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                } finally

                {
                    if (mReader != null) {
                        try {
                            mReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
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
     * 写出数据
     *
     * @param params 将要写出的数据
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private static void writeData(String params) throws IOException,
            UnsupportedEncodingException {
        OutputStream outputStream = mConnection.getOutputStream();
        outputStream.write(params.getBytes("UTF-8"));
        outputStream.flush();

    }

    /**
     * 设置网络连接的参数
     */
    private static void setParams() {
        try {
            mConnection.setRequestMethod("POST");
            mConnection.setDoInput(true);
            mConnection.setDoOutput(true);
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
}
