package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;

import ui.test.cn.xiaoyitong.R;

public class SchoolHistoryMainActivity extends AppCompatActivity {
    TimePickerView pvTime;
    TextView tt;
    EditText termedit,monthedit;
    Button b;
    private ArrayList<String> term = new ArrayList<>();
    private ArrayList<String> month = new ArrayList<>();
    private ArrayList<String> month2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_history_main);
        b= (Button) findViewById(R.id.ok);
        termedit= (EditText) findViewById(R.id.term);
        termedit.setInputType(InputType.TYPE_NULL);
        monthedit= (EditText) findViewById(R.id.month);
        monthedit.setInputType(InputType.TYPE_NULL);
        term.add("2016-2017学年第二学期");
        term.add("2017-2018学年第一学期");

        month.add("二月");
        month.add("三月");
        month.add("四月");
        month.add("五月");
        month.add("六月");
        month.add("七月");


        month2.add("八月");
        month2.add("九月");
        month2.add("十月");
        month2.add("十一月");
        month2.add("十二月");
        month2.add("一月");
        termedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init();
            }
        });
        monthedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termedit.getText().toString()!=null){
                    if (termedit.getText().toString().equals("2016-2017学年第二学期")){

                        month();
                    }
                    if (termedit.getText().toString().equals("2017-2018学年第一学期")){

                        month2();

                    }
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t=monthedit.getText().toString();

                Intent intent=new Intent(SchoolHistoryMainActivity.this,SchoolHistory_Activity.class);
                intent.putExtra("month",t);
                startActivity(intent);
            }
        });



    }


    public void init( ) {
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = term.get(options1);
                termedit.setText(tx);
                if (tx.equals("2017-2018学年第一学期")){
                    monthedit.setText("二月");
                }
                if (tx.equals("2016-2017学年第二学期")){
                    monthedit.setText("八月");
                }

            }
        }).build();
        pvOptions.setPicker(term);
        pvOptions.show();

    }
    public void month( ) {
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = month.get(options1);
                monthedit.setText(tx);
            }
        }).build();
        pvOptions.setPicker(month);
        pvOptions.show();

    }
    public void month2( ) {
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = month2.get(options1);
                monthedit.setText(tx);

            }
        }).build();
        pvOptions.setPicker(month2);
        pvOptions.show();

    }

    }
