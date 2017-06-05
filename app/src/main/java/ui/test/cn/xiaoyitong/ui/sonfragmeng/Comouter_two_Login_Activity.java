package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import org.json.JSONException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.InternetUtils.Computer_Two_Http_util;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Computer_Two_Exam;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;


public class Comouter_two_Login_Activity extends AppCompatActivity {
    EditText computer_Idcard, computer_name, computer_examlevel, computer_subject;
    List<String> level_list = new ArrayList<>();
    List<String> examCount_list = new ArrayList<>();
    Button button;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    level_list.addAll((List<String>) msg.obj);
                    break;
                case 2:
                    examCount_list.addAll((List<String>) msg.obj);
                    break;
                case 3:
                    Computer_Two_Exam computer_two_exam = (Computer_Two_Exam) msg.obj;
                    Log.d("ce", "考试" + computer_two_exam.toString());
                    getScorce(computer_two_exam);
                    break;
            }

        }
    };
    private TextView biaoti;
    private ImageView back;


    private void getScorce(Computer_Two_Exam computer_two_exam) {
        Log.d("ce", "考试" + computer_two_exam.toString());
        if (!computer_two_exam.getScore().equals("")) {
            Intent intent = new Intent(Comouter_two_Login_Activity.this, ComputerTwoScoreMainActivity.class);
            intent.putExtra("idCard", computer_two_exam.getIdCard());
            intent.putExtra("name", computer_two_exam.getName());
            intent.putExtra("examName", computer_two_exam.getExamName());
            intent.putExtra("level", computer_two_exam.getExamNumber());
            intent.putExtra("scorce", computer_two_exam.getScore());
            startActivity(intent);
        } else {
            Toast.makeText(Comouter_two_Login_Activity.this, "信息不正确 请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.comouter_two__login);

        biaoti= (TextView) findViewById(R.id.biaoti);
        biaoti.setText("二级查询");
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        computer_examlevel = (EditText) findViewById(R.id.comouter_two__login_exmalevel);//二级Java
        computer_examlevel.setInputType(InputType.TYPE_NULL);
        computer_Idcard = (EditText) findViewById(R.id.comouter_two__login_IdCard);
        computer_name = (EditText) findViewById(R.id.comouter_two__login_name);
        computer_subject = (EditText) findViewById(R.id.comouter_two__login_exmaSubject);//第几次考试
        computer_subject.setInputType(InputType.TYPE_NULL);
        button = (Button) findViewById(R.id.comouter_two__login_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!computer_examlevel.getText().toString().equals("") && !computer_subject.getText().toString().equals("") && !computer_Idcard.getText().toString().equals("") && !computer_name.getText().toString().equals("")) {
                        Log.d("ce", "等级" + computer_examlevel.getText().toString());
                        Log.d("ce", "等级" + computer_name.getText().toString());
                        Log.d("ce", "等级" + computer_Idcard.getText().toString());
                        Log.d("ce", "等级" + computer_subject.getText().toString());
                        getComputerScorce();
                    } else {
                        Toast.makeText(Comouter_two_Login_Activity.this, "请输入正确的数据", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });





        try {
            getlevel();
            Log.d("ce", "执行方法");
            getExamCount();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        computer_examlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLevel();
            }
        });
        computer_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExamCount();
            }
        });

    }

    public void getComputerScorce() throws IOException, JSONException {//查询
        int id = 0;
        //Toast.makeText(Comouter_two_Login_Activity.this,computer_subject.getText().toString(),Toast.LENGTH_SHORT).show();
        if (computer_examlevel.getText().toString().equals("一级计算机基础及MS Office应用")) {
            id = 1;
        }
        if (computer_examlevel.getText().toString().equals("二级MS Office高级应用")) {
            id = 2;
        }
        if (computer_examlevel.getText().toString().equals("二级JAVA")) {
            id = 3;
        }
        if (computer_examlevel.getText().toString().equals("二级C语言程序设计")) {
            id = 4;
        }
        if (computer_examlevel.getText().toString().equals("二级VFP数据库程序设计")) {
            id = 5;
        }
        if (computer_examlevel.getText().toString().equals("二级VB语言程序设计")) {
            id = 8;
        }
        Log.d("ce", computer_examlevel.getText().toString());

        //   String url = "http://123.207.126.132:8080/computer_two_test/ComputerAppServlet?opt=getdata&name=" + URLEncoder.encode(computer_name.getText().toString()) + "&idCard=" + computer_Idcard.getText().toString() + "&examName=" + URLEncoder.encode(computer_subject.getText().toString()) + "&id=" + id;
        Log.d("ce", "http://192.168.1.104:8080/computer_two_test/ComputerAppServlet?opt=getdata&name=" + computer_name.getText().toString() + "&idCard=" + computer_Idcard.getText().toString() + "&examName=" + computer_subject.getText().toString() + "&id=" + id);
        String url = "http://123.207.126.132:8080/computer_two_test/ComputerAppServlet?opt=getdata&name=" + URLEncoder.encode(computer_name.getText().toString()) + "&idCard=" + computer_Idcard.getText().toString() + "&examName=" + URLEncoder.encode(computer_subject.getText().toString()) + "&id=" + id;

        Log.d("ce", "执行 " + url);
        Computer_Two_Http_util.computerTwoScorce(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Log.d("ce", "执行回调");
                Message mes = new Message();

                mes.what = 3;
                mes.obj = respones;
                handler.sendMessage(mes);
            }

            @Override
            public void onError(Exception e) {

            }
        });
//        }else {
//            Toast.makeText(Comouter_two_Login_Activity.this,"请输入正确的数据",Toast.LENGTH_SHORT).show();
//        }
    }

    public void getlevel() throws IOException, JSONException {//一级二级
        String url = "http://123.207.126.132:8080/computer_two_test/ComputerAppServlet?opt=getExamtext";
        Computer_Two_Http_util.computerTwoHttp(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Log.d("ce", "执行回调");
                Message mes = new Message();
                mes.what = 1;
                mes.obj = respones;
                handler.sendMessage(mes);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    public void getExamCount() throws IOException, JSONException {//第几次考试
        String url = "http://123.207.126.132:8080/computer_two_test/ComputerAppServlet?opt=getExamCount";
        Computer_Two_Http_util.computerTwoHttpExamCount(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Log.d("ce", "执行回调");
                Message mes = new Message();
                mes.what = 2;
                mes.obj = respones;
                handler.sendMessage(mes);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    public void setLevel() {

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = level_list.get(options1);
                computer_examlevel.setText(tx);
            }
        }).isDialog(true).build();
        pvOptions.setPicker(level_list);
        pvOptions.show();
    }

    public void setExamCount() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = examCount_list.get(options1);
                computer_subject.setText(tx);
            }
        }).isDialog(true).build();
        pvOptions.setPicker(examCount_list);
        pvOptions.show();
    }
}
