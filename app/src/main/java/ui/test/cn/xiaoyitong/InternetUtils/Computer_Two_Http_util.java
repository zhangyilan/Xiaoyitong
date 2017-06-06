package ui.test.cn.xiaoyitong.InternetUtils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.entity.Computer_Two_Exam;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;

/**
 * Created by lenovo on 2017/05/09.
 */

public class Computer_Two_Http_util {


    public static void computerTwoHttp(final String url, final HttpCallBackListener listener) throws IOException, JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("aa", "测试中" + url);
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response responses = null;
                String rs = null;
                Log.d("aa", "测试中" + url);
                try {
                    responses = okHttpClient.newCall(request).execute();
                    rs = responses.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<String> list = new ArrayList<>();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(rs.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        list.add(jsonObject.getString("levelName") + jsonObject.getString("subjectName"));
                    }
                    if (listener != null) {
                        listener.onFinish(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void computerTwoScorce(final String url, final HttpCallBackListener listener) throws IOException, JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("aa", "测试中" + url);
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response responses = null;
                String rs = null;
                Log.d("aa", "测试中" + url);
                try {
                    responses = okHttpClient.newCall(request).execute();
                    rs = responses.body().string();
                    Log.d("ce", "数据获取" + rs.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Computer_Two_Exam computer_two_exam = new Computer_Two_Exam();

                try {

                    // JSONObject jsonObject=jsonArray.getJSONObject(i);
                    //  jsonObject.getString("levelName")+jsonObject.getString("subjectName"));
                    Log.d("aa", "json中中中1" + rs.toString());
                    if (!rs.toString().equals("{}")) {


                        JSONObject jsonObject = new JSONObject(rs.toString());
                        computer_two_exam.setName(jsonObject.getString("name"));
                        computer_two_exam.setExamName(jsonObject.getString("examName"));
                        computer_two_exam.setIdCard(jsonObject.getString("idCard"));
                        Log.d("aa", "json中中中" + jsonObject.getString("score"));
                        computer_two_exam.setScore(jsonObject.getString("score"));

                        if (jsonObject.getString("examNumber").equals("1")) {
                            computer_two_exam.setExamNumber("一级计算机基础及MS Office应用");
                        }
                        if (jsonObject.getString("examNumber").equals("2")) {
                            computer_two_exam.setExamNumber("二级MS Office高级应用");
                        }
                        if (jsonObject.getString("examNumber").equals("3")) {
                            computer_two_exam.setExamNumber("二级JAVA");
                        }
                        if (jsonObject.getString("examNumber").equals("4")) {
                            computer_two_exam.setExamNumber("二级C语言程序设计");
                        }
                        if (jsonObject.getString("examNumber").equals("5")) {
                            computer_two_exam.setExamNumber("二级VFP数据库程序设计");
                        }
                        if (jsonObject.getString("examNumber").equals("6")) {
                            computer_two_exam.setExamNumber("二级VB语言程序设计");
                        }

                    } else {
                        computer_two_exam.setScore("");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (listener != null) {
                        Log.d("aa", "測測測" + computer_two_exam.toString());
                        try {
                            listener.onFinish(computer_two_exam);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }).start();
    }


    public static void computerTwoHttpExamCount(final String url, final HttpCallBackListener listener) throws IOException, JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("aa", "测试中" + url);
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response responses = null;
                String rs = null;
                Log.d("aa", "测试中" + url);
                try {
                    responses = okHttpClient.newCall(request).execute();
                    rs = responses.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<String> list = new ArrayList<>();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(rs.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        list.add(jsonObject.getString("name"));
                    }
                    if (listener != null) {
                        listener.onFinish(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


}
