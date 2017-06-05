package ui.test.cn.xiaoyitong.httpHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.entity.Careerpublish;

/**
 * Created by lenovo on 2017/05/28.
 */

public class http1 {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/msword; charset=utf-8");
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET1 = "utf-8"; // 设置编码
    static String BOUNDARY = UUID.randomUUID().toString();
    static String PREFIX = "--", LINEND = "\r\n";
    static String MULTIPART_FROM_DATA = "multipart/form-data";
    static String CHARSET = "UTF-8";

    public static void getdepartment(final String address, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(address).build();
                Response responses = null;
                String rs = null;
                Log.d("aa", "测试中" + address);
                try {
                    responses = okHttpClient.newCall(request).execute();
                    rs = responses.body().string();
                    Log.d("aa", "测试中" + rs.toString());
                } catch (IOException e) {
                    e.printStackTrace();

                    Looper.prepare();
                    listener.onError(e);
                    Looper.loop();
                }
                try {
                    if (listener != null) {
                        Looper.prepare();
                        listener.onFinish(rs.toString());
                        Log.d("ce","返回回调"+rs.toString());
                        Looper.loop();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    listener.onError(e);
                    Looper.loop();
                }
            }
        }).start();
    }

    /**
     * 上传文件到服务器
     * //* @param file 需要上传的文件
     *
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static void uploadFile(final File file, final String RequestURL, final Map<String, String> postData, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int res = 0;
                String result = null;
                String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
                String PREFIX = "--", LINE_END = "\r\n";
                String CONTENT_TYPE = "multipart/form-data"; // 内容类型
                Log.d("ce", "url" + RequestURL);
                try {
                    URL url = new URL(RequestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(TIME_OUT);
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setDoInput(true); // 允许输入流
                    conn.setDoOutput(true); // 允许输出流
                    conn.setUseCaches(false); // 不允许使用缓存
                    conn.setRequestMethod("POST"); // 请求方式
                    conn.setRequestProperty("Charset", CHARSET); // 设置编码
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
                    conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);


                    Log.d("tag", "大小" + file.length() + "");
                    if (file != null) {
                        /**
                         * 当文件不为空时执行上传
                         */
                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                        StringBuffer sb = new StringBuffer();

                        for (Map.Entry<String, String> entry : postData.entrySet()) {
                            sb.append(PREFIX);
                            sb.append(BOUNDARY);
                            sb.append(LINE_END);
                            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
                            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
                            sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                            sb.append(PREFIX);
                            sb.append(entry.getValue());
                            sb.append(PREFIX);
                        }

                        sb.append(PREFIX);
                        sb.append(BOUNDARY);
                        sb.append(LINE_END);
                        /**
                         * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                         * filename是文件的名字，包含后缀名
                         */
                        Log.d("ce", "文件名" + file.getName());
                        sb.append("Content-Disposition: form-data; name=\"file1\"; filename=\""
                                + file.getName() + "\"" + LINE_END);
                        sb.append("Content-Type: application/octet-stream; charset="
                                + CHARSET + LINE_END);
                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());
                        InputStream is = new FileInputStream(file);

                        Log.d("ce", "文件名1");
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                        }
                        Log.d("ce", "文件名2");
                        is.close();
                        dos.write(LINE_END.getBytes());
                        Log.d("ce", "文件名3");
                        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                                .getBytes();
                        dos.write(end_data);
                        dos.flush();
                        /**
                         * 获取响应码 200=成功 当响应成功，获取响应的流
                         */
                        Log.d("ce", sb.toString());
                        res = conn.getResponseCode();
                        Log.d("ce", conn.getContent().toString());
                        Log.e("ce2", "响应码:" + res);
                        if (res == 200) {
                            Log.e("ce", "request success");
                            InputStream input = conn.getInputStream();
                            StringBuffer sb1 = new StringBuffer();
                            int ss;
                            while ((ss = input.read()) != -1) {
                                sb1.append((char) ss);
                            }
                            result = sb1.toString();
                            try {
                                listener.onFinish(result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("ce", "响应失败");
                        }
                    }
                } catch (MalformedURLException e) {
                    Looper.prepare();
                    listener.onError(e);
                    Log.e("ce", "MalformedURLException");
                    Looper.loop();
                } catch (IOException e) {
                    Looper.prepare();
                    listener.onError(e);
                    Log.e("ce", "IOException");
                    Looper.loop();
                }
            }
        }).start();
    }

    public static void postAsynFile(final String url, final File file, final Careerpublish careerpublish, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("publish_branch", careerpublish.getPublish_branch())
                            .addFormDataPart("quality_frade", careerpublish.getQuality_frade())
                            .addFormDataPart("theme", careerpublish.getTheme())
                            .addFormDataPart("include", careerpublish.getInclude())
                            .addFormDataPart("publish_time", careerpublish.getPublish_time())
                            .addFormDataPart("start_time", careerpublish.getStart_time())
                            .addFormDataPart("end_time", careerpublish.getEnd_time())
                            .addFormDataPart("activity_address", careerpublish.getActivity_address())
                            .addFormDataPart("activity_type", careerpublish.getActivity_type())
                            .addFormDataPart("activity_background", careerpublish.getActivity_background())
                            .addFormDataPart("activity_suggest", careerpublish.getActivity_suggest())//上面是加数据，顺序要与服务器一致
                            .addFormDataPart("file1", file.getName(), fileBody)//加文件
                            .build();

                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    final int[] res = new int[1];
                    mOkHttpClient.newCall(request).enqueue(new Callback() {//响应
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("ce", "异常");
                            Looper.prepare();
                            listener.onError(e);

                            Looper.loop();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("ce", "响应" + response.code());

                            if (response.code() == 200) {
                                if (listener != null) {
                                    try {
                                        listener.onFinish(response.code());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                if (listener != null) {
                                    try {
                                        listener.onFinish(response.code());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                    });
                    Log.e("ce", String.valueOf(res[0]));

                } catch (Exception e) {
                    Looper.prepare();
                    listener.onError(e);

                    Looper.loop();
                }
            }
        }).start();

    }


    public static void sendRequest(final String adress, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    List<Careerpublish> list = new ArrayList<>();
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url(adress).build();
                    Response responses = null;
                    String rs = null;
                    Log.d("aa", "测试中" + adress);

                    responses = okHttpClient.newCall(request).execute();
                    rs = responses.body().string();
                    Log.d("aa", "测试中" + rs.toString());


                    int id;
                    String them = null;
                    String publish_branch = null;
                    String sorce = null;
                    String publish_time = null;
                    String state = null;
                    Bitmap activity_img = null;
                    Log.d("ce", "解中" + rs.toString());
                    JSONArray jsonArray = new JSONArray(rs.toString());
                    Log.d("ce", "长度" + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("ce", "解中");
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        Log.d("ce", "解中"+jsonobject);
                        id = Integer.parseInt(jsonobject.getString("id"));
                        them = jsonobject.getString("theme");
                        publish_branch = jsonobject.getString("publish_branch");
                        publish_time = jsonobject.getString("publish_time");
                        sorce = jsonobject.getString("quality_frade");
                        state = jsonobject.getString("state");
                        String imgurl = jsonobject.getString("activity_img");
                      //  activity_img = parseImg(imgurl);

                        Careerpublish careerpublish = new Careerpublish();
                        careerpublish.setId(id);
                        careerpublish.setTheme(them);
                        careerpublish.setPublish_branch(publish_branch);
                        careerpublish.setPublish_time(publish_time);
                        careerpublish.setQuality_frade(sorce);
                      //  careerpublish.setImgBitmap(activity_img);
                        careerpublish.setStatus(state);
                        list.add(careerpublish);
                    }
                    if (listener != null) {
                        listener.onFinish(list);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    listener.onError(e);
                    Looper.loop();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    listener.onError(e);
                    Looper.loop();
                }
            }
        }).start();
    }
    public static void getadmindetail(final String address, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(address).build();
                Response responses = null;
                String rs = null;
                Log.d("aa", "测试中" + address);
                try {
                    responses = okHttpClient.newCall(request).execute();
                    rs = responses.body().string();
                    Log.d("aa", "测试中" + rs.toString());
                } catch (IOException e) {
                    e.printStackTrace();

                    Looper.prepare();
                    listener.onError(e);
                    Looper.loop();
                }
                try {
                    JSONArray jsonArray = new JSONArray(rs.toString());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String title = null;
                    String score = null;
                    String start_time= null;
                    String stop_time= null;
                    String adress = null;
                    String project = null;
                    String department = null;
                    String background = null;
                    String content = null;
                    String imgurl=null;
                    Bitmap bitmap = null;

                    title = jsonObject.getString("theme");
                    score = jsonObject.getString("quality_frade");
                    start_time = jsonObject.getString("start_time");
                    stop_time = jsonObject.getString("end_time");
                    adress = jsonObject.getString("activity_address");
                    project = jsonObject.getString("activity_type");
                    department = jsonObject.getString("publish_branch");
                    background = jsonObject.getString("activity_background");
                    content = jsonObject.getString("include");
                    imgurl = jsonObject.getString("activity_img");


                    bitmap=parseImg(imgurl);

                    Careerpublish careerpublish=new Careerpublish();
                    careerpublish.setTheme(title);
                    careerpublish.setQuality_frade(score);
                    careerpublish.setStart_time(start_time);
                    careerpublish.setEnd_time(stop_time);
                    careerpublish.setActivity_address(adress);
                    careerpublish.setActivity_type(project);
                    careerpublish.setPublish_branch(department);
                    careerpublish.setActivity_background(background);
                    careerpublish.setInclude(content);
                    careerpublish.setImgBitmap(bitmap);


                    if (listener != null) {
                        listener.onFinish(careerpublish);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    listener.onError(e);
                    Looper.loop();
                }
            }
        }).start();
    }

    public static void getStatus(final String address,  final HttpCallBackListener listener) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);

                    Log.d("aaaaa", url.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Log.d("aaaaa", response.toString());
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
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
