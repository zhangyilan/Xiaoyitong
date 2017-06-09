package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http;
import ui.test.cn.xiaoyitong.utils.HttpUtil;

/**
 * Created by asus on 2017/4/22.
 */

public class Courses_login extends SwipeBackActivity {
    String idd, termm;

    boolean aBoolea;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    Log.d("aab", "验证b" + msg.obj.toString().length());

                    if (msg.obj.toString().length()>3) {
                        SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
                        String user_name = share.getString("user_name", "");
                        Intent intent = new Intent(Courses_login.this, Courses.class);

                        intent.putExtra("id", user_name);
                        intent.putExtra("year", term.getText().toString().substring(0, 9));
                        intent.putExtra("term", String.valueOf(term.getText().toString().charAt(11)));
                        startActivity(intent);

                    } else {
                        Toast.makeText(Courses_login.this,"目前暂无您的课表",Toast.LENGTH_SHORT).show();
                    }
            }

        }
    };

    private Button btn_get, btn_back;
    private EditText  term;
    private LinearLayout term_layout;
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> termdis = new ArrayList<>();
    private ArrayList<String> year2 = new ArrayList<>();

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

        for (int i=1970;i<=2037;i++)
        {
            year.add(String.valueOf(i));
        }
        for (int i=1970;i<=2037;i++)
        {
            year2.add(String.valueOf(i));
        }

        termdis.add("1");
        termdis.add("2");

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
                final HttpUtil httpUtil = new HttpUtil();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String dateString = formatter.format(new Date());


                if(httpUtil.isNetworkAvailable(Courses_login.this)) {
                    SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
                    String user_name = share.getString("user_name", "");
                    Log.d("aab", "验证b" + user_name);
                    String a= term.getText().toString().substring(0, 4);
                    String b= term.getText().toString().substring(5, 9);
                    if (Integer.valueOf(user_name.substring(0, 4))<=Integer.valueOf(a)&&Integer.valueOf(a)!=Integer.valueOf(b)&&Integer.valueOf(a)==(Integer.valueOf(b)-1)) {

                        String url = "http://123.206.92.38:80/SimpleSchool/schooltimetableservlet?opt=get_table&school_year=" + term.getText().toString().substring(0, 9) + "&school_term=" + String.valueOf(term.getText().toString().charAt(11)) + "&student_id=" + user_name;

                        http.sendRequest(url, new HttpCallBackListener() {
                            @Override
                            public void onFinish(Object respones) {
                                Message message = new Message();
                                message.obj = respones;
                                message.what = 1;
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onError(Exception e) {

                            }

                        });

                    }else {
                        Toast.makeText(Courses_login.this, "请检查信息是否正确", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Courses_login.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
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
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2037,1,1);
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                term.setText(year.get(options1)+"-"+year2.get(option2) + " 第" + termdis.get(options3) + "学期");

            }
        }).setTitleSize(15).isDialog(true)//是否显示为对话框样式
                .setSelectOptions(47,47, 0)  //设置默认选中项
                .setLabels("-", "年", "  学期")
                .build();
        pvOptions.setNPicker(year, year2, termdis);
        pvOptions.show();

    }
}
