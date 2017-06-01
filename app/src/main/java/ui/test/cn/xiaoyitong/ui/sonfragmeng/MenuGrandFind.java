package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import ui.test.cn.xiaoyitong.uiManger.CustomProgressDialog;
import ui.test.cn.xiaoyitong.utils.HttpUtil;

/**
 * Created by asus on 2017/4/19.
 */

public class MenuGrandFind extends SwipeBackActivity implements Serializable {
    List<Grade> grades;
    Button btn_back;
    List<String> options1Items = new ArrayList<>();
    List<String> options2Items = new ArrayList<>();
    List<String> options3Items = new ArrayList<>();
    String str1, str2, str3, str4;
    TextView get_student_term;
    CustomProgressDialog dialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj.toString().length() > 2) {
                        Intent intent = new Intent(MenuGrandFind.this, MenuGandFind_detail.class);
                        intent.putExtra("grades", (Serializable) grades);
                        startActivity(intent);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(MenuGrandFind.this, "目前没有您的成绩", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    //学号，密码框框的值
    private EditText get_student_id, getpwd;
    private Button get;

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

        dialog = new CustomProgressDialog(MenuGrandFind.this, "正在加载中", R.anim.frame, R.style.MyDialogStyle);


        for (int i = 1970; i <= 2037; i++) {
            options1Items.add(String.valueOf(i));
        }
        for (int i = 1970; i <= 2037; i++) {
            options2Items.add(String.valueOf(i));
        }
        options3Items.add("1");
        options3Items.add("2");
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
                        str3 = options3Items.get(options3);
                        String str = str1 + "-" + str2 + "  第" + str3 + "学期";
                        get_student_term.setText(str);
                    }
                })
                        .setSelectOptions(47, 47, 0)  //设置默认选中项
                        .setLabels("-", "年", "  学期")
                        .build();
                pvOptions.setNPicker(options1Items, options2Items, options3Items);
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


        get = (Button) findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求身份证得到密码验证
                //  final String URL = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=is_self&student_id=" + get_student_id.getText().toString() + "&identity_card=" + getpwd.getText().toString();
                //查询请求
                SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
                String user_name = share.getString("user_name", "没有登陆");

                final String url1 = "http://123.206.92.38:80/SimpleSchool/studentgradeservlet?opt=get_grade&school_year=" +
                        str1 + "-" + str2 + "&school_term=" + str3 + "&student_id=" + user_name;
                Log.d("ce", "url" + url1);
                final HttpUtil httpUtil = new HttpUtil();
                Log.d("ce", "连接" + url1);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                if (httpUtil.isNetworkAvailable(MenuGrandFind.this)) {

                    httpUtil.getData(url1, new HttpCallback() {
                        @Override
                        public void onFinish(String res) {
                            Log.d("aaaaaa", res);

                            json(res);
                            Message message = new Message();
                            message.what = 0;
                            message.obj = res;
                            dialog.dismiss();
                            handler.sendMessage(message);

                        }

                        @Override
                        public void onerror(Exception e) {

                        }
                    });
                } else {
                    Toast.makeText(MenuGrandFind.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

        });


    }

    private void json(String response) {
        grades = new ArrayList<Grade>();
        try {
            Log.d("解析数据中", "kkk");
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
                    Log.d("mmmmm", "lll");
                    grade.setCourse_name(jsonObject.optString("course_name"));
                }
                grade.setGrade_point("未知");
                if (!jsonObject.optString("grade_point").equals("")) {
                    Log.d("jjj", "lll");
                    grade.setGrade_point(jsonObject.optString("grade_point"));
                }
                grades.add(grade);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


