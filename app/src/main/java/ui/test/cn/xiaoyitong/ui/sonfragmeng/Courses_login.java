package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http;

/**
 * Created by asus on 2017/4/22.
 */

public class Courses_login extends SwipeBackActivity {
    String idd, termm;
    String bool = "";
    boolean aBoolea;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    bool = (String) msg.obj;

                    Log.d("aab", "验证b" + bool);
                    Log.d("aab","验证hap"+bool);
                    if (bool.equals("true")) {
                        Intent intent = new Intent(Courses_login.this, Courses.class);
                        intent.putExtra("id", id.getText().toString());
                        intent.putExtra("year", term.getText().toString().substring(0, 9));
                        intent.putExtra("term", String.valueOf(term.getText().toString().charAt(11)));
                        startActivity(intent);
                    } else {
                        Toast.makeText(Courses_login.this, "密码不正确 请重新输入", Toast.LENGTH_SHORT).show();
                    }
            }

        }
    };
    private Button btn_get, btn_back;
    private EditText id, password, term;
    private LinearLayout term_layout;
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> termdis = new ArrayList<>();
    private ArrayList<String> tt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.menu_kebiaofind);
        year.add("2015-2016");
        year.add("2016-2017");

        termdis.add("1");
        termdis.add("2");
        id = (EditText) findViewById(R.id.getid);
        password = (EditText) findViewById(R.id.getpassword);
       // term_layout= (LinearLayout) findViewById(R.id.get_student_termlayout);
        term = (EditText) findViewById(R.id.get_student_term);
        term.setInputType(InputType.TYPE_NULL);
        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTerm();
            }
        });
        btn_get = (Button) findViewById(R.id.btn_getid);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    if (id.getText().toString().substring(0, 4).equals("2016") && term.getText().toString().substring(0, 4).equals("2015")) {
                        Toast.makeText(Courses_login.this, "你在2015-2016你年没有课表", Toast.LENGTH_SHORT).show();
                    } else {
                        String url = "http://123.206.92.38/SimpleSchool/userservlet?opt=is_self&student_id=" + id.getText().toString() + "&identity_card=" + password.getText().toString();

                        http.identitytest(url, new HttpCallBackListener() {
                            @Override
                            public void onFinish(Object respones) {
                                Message message = new Message();
                                Log.d("aab", "验证z" + respones.toString());
                                message.obj = respones.toString();
                                message.what = 1;
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                    }

                } else if (id.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(Courses_login.this, "用户名或密码不能为空  请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Courses_login.this, "密码不正确  请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTerm() {

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = year.get(options1) + termdis.get(option2);
                term.setText(year.get(options1) + " 第" + termdis.get(option2) + "学期");
                idd = year.get(options1).toString();
                termm = termdis.get(option2).toString();
            }
        }).setTitleSize(15).isDialog(true)//是否显示为对话框样式
                .setLabels("  年", "学期     ", null).build();
        pvOptions.setNPicker(year, termdis, null);
        pvOptions.show();

    }
}
