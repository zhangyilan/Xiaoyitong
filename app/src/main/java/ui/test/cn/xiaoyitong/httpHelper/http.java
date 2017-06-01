package ui.test.cn.xiaoyitong.httpHelper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 2017/04/19.
 */

public class http {
    public static void sendRequest(final String address, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[][] contents = new String[6][7];
                HttpURLConnection connection = null;
                try {
                    String a = "周一第9,10节";
                    Log.d("ce", "长度" + a.length());
                    Log.d("ce", "发送中" + address);
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    Log.d("ce", "发送中");
                    connection.setRequestMethod("GET");
                    Log.d("ce", "发送中");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);

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
                    if (response.toString().length()>2) {

                        String[][] content = new String[5][7];
                        JSONArray jsonArray = new JSONArray(response.toString());
                        String school_year = null;//学年  "school_year": "2016-2017",
                        String school_term = null;//学期"school_term": 1,
                        String student_id = null;//学号"student_id": "20164429",
                        int week;//星期几"week": 4,
                        int Section;//开始上课节次 "Section": 1,
                        String study_long = null;//上课长度"study_long": 2,
                        String study_time = null;//上课时间"study_time": "周四第1,2节{第3-18周}",
                        String study_place = null;//上课地点"study_place": "第二运动场1",
                        String course_name = null;//课程名字"course_name": "体育1"
                        String string = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("ce", "解中");
                            JSONObject jsonobject = jsonArray.getJSONObject(i);

                            school_year = jsonobject.getString("school_year");
                            school_term = jsonobject.getString("school_term");
                            student_id = jsonobject.getString("student_id");
                            week = Integer.parseInt(jsonobject.getString("week"));
                            Section = Integer.parseInt(jsonobject.getString("Section"));
                            study_long = jsonobject.getString("study_long");
                            study_time = jsonobject.getString("study_time");
                            study_place = jsonobject.getString("study_place");
                            study_long = jsonobject.getString("study_long");
                            course_name = jsonobject.getString("course_name");

                            Log.d("aa", course_name);
                            if (study_time.contains(";")) {
                                String[] s = study_time.split(";");
                                String[] place = study_place.split(";");

                                for (int k = 0; k < s.length; k++) {
                                    Log.d("aad", "截取数据" + s[k]);

                                    if (s[k].contains("周一")) {
                                        int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                        String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{", "").replace("}", "");
                                        Log.d("aad", "截取数据" + row);
                                        content[(row + 1) / 2 - 1][0] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                    }
                                    if (s[k].contains("周二")) {
                                        int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                        Log.d("aad", "截取数据" + row);
                                        String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{", "").replace("}", "");
                                        content[(row + 1) / 2 - 1][1] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                    }
                                    if (s[k].contains("周三")) {
                                        int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                        Log.d("aad", "截取数据" + row);
                                        String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{", "").replace("}", "");
                                        content[(row + 1) / 2 - 1][2] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                    }
                                    if (s[k].contains("周四")) {
                                        int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                        Log.d("aad", "截取数据" + row);
                                        String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{", "").replace("}", "");
                                        content[(row + 1) / 2 - 1][3] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                    }
                                    if (s[k].contains("周五")) {
                                        int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                        Log.d("aad", "截取数据" + row);
                                        String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{", "").replace("}", "");
                                        content[(row + 1) / 2 - 1][4] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                    }
                                    if (s[k].contains("周六")) {
                                        int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                        Log.d("aad", "截取数据" + row);
                                        String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{", "").replace("}", "");
                                        content[(row + 1) / 2 - 1][5] = course_name + "<Br>" + str1 + "<Br>" + study_place;
                                    }
                                    if (s[k].contains("周日")) {
                                        int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                        Log.d("aad", "截取数据" + row);
                                        String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{", "").replace("}", "");
                                        content[(row + 1) / 2 - 1][6] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                    }
                                }
                            } else {
                                String str1 = study_time.substring(study_time.indexOf("{"), study_time.length()).replace("{", "").replace("}", "");
                                Log.d("aad", "hhh" + str1);
                                content[(Section + 1) / 2 - 1][week - 1] = course_name + "<Br>" + str1 + "<Br>" + study_place;
                            }
                            Log.d("ce", "监听器为" + ((Section + 1) / 2 - 1) + "," + (week - 1));
                            Log.d("ce", "监听器为515" + content[(Section + 1) / 2 - 1][week - 1]);
                        }

                        Log.d("ce", "监听器为");
                        if (listener != null) {
                            //回调方法onfinish
                            Log.d("ce", "开始回调" + content[4][0]);
                            listener.onFinish(content);
                        }
                    }else {
                        if (listener != null) {
                            //回调方法onfinish

                            listener.onFinish(response.toString());
                        }
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
    public static void identitytest(final String address, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {

                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    Log.d("ce", "发送中");
                    connection.setRequestMethod("GET");
                    Log.d("ce", "发送中");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);

                    Log.d("ce", "接受1");
                    InputStream in = connection.getInputStream();
                    Log.d("ce", "接受中2");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    if (listener != null) {
                        //回调方法onfinish
                        Log.d("aab","验证"+response.toString());
                        listener.onFinish(response.toString());
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
}
