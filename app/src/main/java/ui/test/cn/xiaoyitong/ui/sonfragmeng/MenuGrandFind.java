package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Grade;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallback;
import ui.test.cn.xiaoyitong.utils.HttpUtil;

/**
 * Created by asus on 2017/4/19.
 */

public class MenuGrandFind extends SwipeBackActivity implements Serializable {
    //学号，密码框框的值
    private EditText get_student_id, getpwd;
    private Button get;
    List<Grade> grades;
    Button btn_back;
    List<String> options1Items = new ArrayList<>();
    List<String> options2Items = new ArrayList<>();
    String str1, str2, str3, str4;
    TextView get_student_term;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.menu_grandfind);


        options1Items.add("2014-2015");
        options1Items.add("2015-2016");
        options1Items.add("2016-2017");
        options2Items.add("1");
        options2Items.add("2");
        get_student_term = (TextView) findViewById(R.id.get_student_term);
        get_student_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(MenuGrandFind.this, new OptionsPickerView.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        str1 = options1Items.get(options1);
                        str2 = options2Items.get(options2);
                        String str = str1 + "  第" + str2 + "学期";
                        get_student_term.setText(str);
                    }
                }).build();
                pvOptions.setNPicker(options1Items, options2Items, null);
                pvOptions.show();
            }
        });
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        get_student_id = (EditText) findViewById(R.id.get_student_id);
        //得到密码验证==身份证后六位
        getpwd = (EditText) findViewById(R.id.getpwd);

        get = (Button) findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求身份证得到密码验证
                final String URL = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=is_self&student_id=" + get_student_id.getText().toString() + "&identity_card=" + getpwd.getText().toString();
                //查询请求
                final String url1 = "http://123.206.92.38:80/SimpleSchool/studentgradeservlet?opt=get_grade&school_year=" +
                        str1 +"&school_term=" +str2+"&student_id=" +get_student_id.getText().toString();

                final HttpUtil httpUtil = new HttpUtil();
                if (httpUtil.isNetworkAvailable(MenuGrandFind.this)) {
                    httpUtil.getData(URL, new HttpCallback() {
                        @Override
                        public void onFinish(String respose) {
                            if (respose.equals("true")) {
                                httpUtil.getData(url1, new HttpCallback() {
                                    @Override
                                    public void onFinish(String res) {
                                        json(res);
                                        Message message = new Message();
                                        message.what = 0;
                                        handler.sendMessage(message);
                                    }

                                    @Override
                                    public void onerror(Exception e) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onerror(Exception e) {

                        }
                    });
                }
            }
        });
    }

    private void json(String response) {
        grades = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int a = 0; a < jsonArray.length(); a++) {
                JSONObject jsonObject = jsonArray.getJSONObject(a);
                Grade grade = new Grade();

                String str = jsonObject.optString("course_grade");
                if (str.equals("")) {
                    grade.setCourse_grade("未知");
                } else {
                    grade.setCourse_grade(jsonObject.optString("course_grade"));
                }
                if (!jsonObject.optString("course_name").equals("")) {
                    grade.setCourse_name(jsonObject.optString("course_name"));
                }
                grade.setGrade_point("未知");
                if (!jsonObject.optString("grade_point").equals("")) {
                    grade.setGrade_point(jsonObject.optString("grade_point"));
                }
                grades.add(grade);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(MenuGrandFind.this, MenuGandFind_detail.class);
                    intent.putExtra("grades", (Serializable) grades);
                    startActivity(intent);
                    break;
            }
        }
    };
}


