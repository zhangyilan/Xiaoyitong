package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.uiManger.CustomProgressDialog;

/**
 * Created by asus on 2017/4/22.
 */

public class Courses_login extends SwipeBackActivity {
    private Button btn_get, btn_back;
    private EditText id, password, term;
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> termdis = new ArrayList<>();
    private ArrayList<String> tt = new ArrayList<>();
    String idd, termm;

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
                final CustomProgressDialog dialog = new CustomProgressDialog(Courses_login.this, "正在加载中", R.anim.frame, R.style.MyDialogStyle);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                if (id.getText().toString().equals(password.getText().toString()) && !id.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    if (id.getText().toString().substring(0, 4).equals("2016") && term.getText().toString().substring(0, 4).equals("2015")) {
                        Toast.makeText(Courses_login.this, "你在2015-2016你年没有课表", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(Courses_login.this, Courses.class);
                        intent.putExtra("id", id.getText().toString());
                        intent.putExtra("year", term.getText().toString().substring(0, 9));
                        intent.putExtra("term", String.valueOf(term.getText().toString().charAt(11)));
                        //  Toast.makeText(Courses_login.this,term.getText().toString().substring(0,9)+String.valueOf(term.getText().toString().charAt(11)),Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(intent);
                    }
                } else if (id.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(Courses_login.this, "用户名或密码不能为空  请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    password.setText("");
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
                term.setText(year.get(options1) + "  第" + termdis.get(option2) + "学期");
                idd = year.get(options1).toString();
                termm = termdis.get(option2).toString();
            }
        }).setTitleSize(15).isDialog(true)//是否显示为对话框样式
                .setLabels("  年", "学期     ", null).build();
        pvOptions.setNPicker(year, termdis, null);
        pvOptions.show();

    }
}
