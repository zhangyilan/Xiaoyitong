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
                    URL url = new URL(address);
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

                        if (study_time.contains(";")) {
                            String[] s = study_time.split(";");
                            String[] place=study_place.split(";");

                            for (int k = 0; k < s.length; k++) {

                                if (s[k].contains("周一")) {
                                    int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                    String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{","").replace("}","");

                                    content[(row + 1) / 2 - 1][0] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                }
                                if (s[k].contains("周二")) {
                                    int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                    String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{","").replace("}","");
                                    content[(row + 1) / 2 - 1][1] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                }
                                if (s[k].contains("周三")) {
                                    int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                    String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{","").replace("}","");
                                    content[(row + 1) / 2 - 1][2] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                }
                                if (s[k].contains("周四")) {
                                    int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                    String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{","").replace("}","");
                                    content[(row + 1) / 2 - 1][3] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                }
                                if (s[k].contains("周五")) {
                                    int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                    String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{","").replace("}","");
                                    content[(row + 1) / 2 - 1][4] =course_name + "<Br>" + str1+ "<Br>" + place[k];
                                }
                                if (s[k].contains("周六")) {
                                    int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                    String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{","").replace("}","");
                                    content[(row + 1) / 2 - 1][5] = course_name + "<Br>" + str1 + "<Br>" + study_place;
                                }
                                if (s[k].contains("周日")) {
                                    int row = Integer.parseInt(String.valueOf(s[k].charAt(3)));
                                    String str1 = s[k].substring(s[k].indexOf("{"), s[k].length()).replace("{","").replace("}","");
                                    content[(row + 1) / 2 - 1][6] = course_name + "<Br>" + str1 + "<Br>" + place[k];
                                }
                            }
                        } else {
                            String str1 = study_time.substring(study_time.indexOf("{"), study_time.length()).replace("{","").replace("}","");
                            content[(Section + 1) / 2 - 1][week - 1] = course_name + "<Br>" + str1 + "<Br>" + study_place;
                        }
                    }
                    if (listener != null) {
                        //回调方法onfinish
                        listener.onFinish(content);
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
