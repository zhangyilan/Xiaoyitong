package ui.test.cn.xiaoyitong.httpHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ui.test.cn.xiaoyitong.entity.news;

/**
 * Created by lenovo on 2016/12/20.
 */

public class Httputil {

    public static void sendRequest(final String address, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<news> list = new ArrayList<news>();
                HttpURLConnection connection = null;
                try {
                    Log.d("ce", "发送中");
                    Log.d("ce", "发送中" + address);
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    Log.d("ce", "发送中");
                    connection.setRequestMethod("GET");
                    Log.d("ce", "发送中");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    Log.d("ce", "接受1");
                    InputStream in = connection.getInputStream();
                    Log.d("ce", "接受中2");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Log.d("ce", "得到json");
                    JSONArray jsonArray = new JSONArray(response.toString());
                    int id;
                    String title = null;
                    String time = null;
                    String author = null;
                    int read;


                    for (int i = 0; i < jsonArray.length(); i++) {
                        Bitmap bitmap = null;
                        Log.d("ce", "解中");
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        id = Integer.parseInt(jsonobject.getString("id"));
                        title = jsonobject.getString("title");
                        author = jsonobject.getString("author");
                        time = jsonobject.getString("postime");
                        read = Integer.parseInt(jsonobject.getString("read"));
                        JSONArray imgname = jsonobject.getJSONArray("imgname");
                        Log.d("ce", "数组" + imgname.toString());
                        if (imgname != null) {
                            Log.d("ce", "数组" + imgname.toString());
                            String img = null;
                            for (int j = 0; j < imgname.length(); j++) {

                                img = (String) imgname.get(0);
                                int length = img.length();
                                Log.d("ce", "length" + length);
                                Log.d("ce", "img" + img);
                                Log.d("ce", "得到图片中");
                            }
                            if (img != null && img.length() < 20) {
                                String imgUrl = "http://123.206.92.38/SimpleSchool/imgss/" + img;
                                Log.d("ce", "图片路径" + imgUrl);
                                bitmap = parseImg(imgUrl);
                                Log.d("ce", "图片路径发送中");
                            }
                        }
                        Log.d("ce", "id" + id);
                        Log.d("ce", "title" + title);
                        //String imgUrl="http://119.29.114.210:8080/mybookshop/"+imgurl;
                        //bitmap=parseImg(imgUrl);

                        //Log.d("ce","响应中"+String.valueOf(bitmap));
                        news news = new news();
                        news.setId(id);
                        news.setTitle(title);
                        news.setAuthor(author);
                        news.setPostime(time);
                        news.setRead(read);
                        news.setImgBitmap(bitmap);
                        Log.d("ce", time);
                        Log.d("ce", String.valueOf(read));
                        Log.d("ce", "列表为" + String.valueOf(list));
                        list.add(news);
                        Log.d("ce", "列表" + String.valueOf(list));
                    }
                    Log.d("ce", "监听器为");
                    if (listener != null) {
                        //回调方法onfinish
                        Log.d("ce", "开始回调");
                        listener.onFinish(list);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onerror方法
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }

            }
        }).start();
    }

    public static void sendcontentrequest(final String address, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {
                    Log.d("ce", "发送中");
                    Log.d("ce", "发送中" + address);
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    Log.d("ce", "发送中");
                    connection.setRequestMethod("GET");
                    Log.d("ce", "发送中");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    Log.d("ce", "接受1");
                    InputStream in = connection.getInputStream();
                    Log.d("ce", "接受中2");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Log.d("ce", "得到json");
                    JSONObject jsonObject = new JSONObject(response.toString());
                    int id;
                    String title = null;
                    String time = null;
                    String author = null;
                    String sourse = null;
                    String content = null;
                    int read;
                    Bitmap bitmap = null;
                    id = Integer.parseInt(jsonObject.getString("id"));
                    title = jsonObject.getString("title");
                    author = jsonObject.getString("author");
                    sourse = jsonObject.getString("source");
                    content = jsonObject.getString("content");
                    time = jsonObject.getString("postime");
                    read = Integer.parseInt(jsonObject.getString("read"));
                    Set<Bitmap> set = new HashSet<Bitmap>();
                    JSONArray imgname = jsonObject.getJSONArray("imgname");
                    Log.d("ce", "数组" + imgname.toString());
                    if (imgname != null) {
                        Log.d("ce", "数组" + imgname.toString());
                        String img = null;
                        for (int j = 0; j < imgname.length(); j++) {
                            img = (String) imgname.get(j);
                            int length = img.length();
                            Log.d("ce", "length" + length);
                            Log.d("ce", "img" + img);
                            Log.d("ce", "得到图片中");
                            if (img != null && img.length() < 20) {
                                // String imgUrl = "http://192.168.31.228:8080/newsapp/imgs/" + img;
                                String imgUrl = "http://123.206.92.38/SimpleSchool/imgss/" + img;
                                Log.d("ce", "图片路径" + imgUrl);
                                bitmap = parseImg(imgUrl);
                                Log.d("ce", "图片路径发送中");
                                set.add(bitmap);
                            }
                        }
                    }
                    Log.d("ce", "id" + id);
                    Log.d("ce", "title" + title);
                    //String imgUrl="http://119.29.114.210:8080/mybookshop/"+imgurl;
                    //bitmap=parseImg(imgUrl);

                    //Log.d("ce","响应中"+String.valueOf(bitmap));
                    news news = new news();
                    news.setId(id);
                    news.setTitle(title);
                    news.setAuthor(author);
                    news.setPostime(time);
                    news.setRead(read);
                    news.setContent(content);
                    news.setSource(sourse);
                    news.setImgBitmap(bitmap);
                    news.setSet(set);
                    Log.d("ce", time);
                    Log.d("ce", String.valueOf(read));
                    Log.d("ce", "news为" + String.valueOf(news));
                    Log.d("ce", "监听器为");
                    if (listener != null) {
                        //回调方法onfinish
                        Log.d("ce", "开始回调");
                        listener.onFinish(news);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onerror方法
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }

            }
        }).start();
    }

    private static Bitmap parseImg(String imgurl) throws IOException {
        HttpURLConnection connection = null;

        URL url = null;
        Bitmap bitmap = null;
        try {

            url = new URL(imgurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream in = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return bitmap;
    }


}
