package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http;
import ui.test.cn.xiaoyitong.httpHelper.http1;

/**
 * 管理员发布界面
 * Created by lenovo on 2017/05/27.
 */

public class Career_Pulish_Activity extends Activity {

    TextView school, department, ministry, starttime, stoptime, score, standclass, standproject;

    LinearLayout departmentlayout, ministrylayout, starttimelayout, stoptimelayout, adresslayout, scorelayout, standclasslayout, standprojectlayout;
    Button next;
    ArrayList<String> options1Items = new ArrayList<>();
    ArrayList<String> scoreList = new ArrayList<>();
    ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    ArrayList<String> standclass1Items = new ArrayList<>();
    ArrayList<ArrayList<String>> standproject2Items = new ArrayList<>();
    int option, option1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.career_pulish_layout);
        getdata();
        getScoredata();
        getstanddata();

        school = (TextView) findViewById(R.id.career_publish_school);
        school.setText("四川交通职业技术学院");
        department = (TextView) findViewById(R.id.career_publish_Department);
        ministry = (TextView) findViewById(R.id.career_publish_ministry);

        starttime = (TextView) findViewById(R.id.career_publish_starttime);
        stoptime = (TextView) findViewById(R.id.career_publish_stoptime);

        score = (TextView) findViewById(R.id.career_publish_score);
        standclass = (TextView) findViewById(R.id.career_publish_standclass);
        standproject = (TextView) findViewById(R.id.career_publish_standproject);
        next = (Button) findViewById(R.id.career_publish_next);
        getSdata();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d1 = starttime.getText().toString();
                String d2 = stoptime.getText().toString();
                System.out.println(d1.compareTo(d2));
                int d=d1.compareTo(d2);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");

                String dateString = formatter.format(new Date());
                int now=dateString.compareTo(d1);



                if (!school.getText().toString().equals(null) && !department.getText().toString().equals(null) && !ministry.getText().toString().equals(null) && !starttime.getText().toString().equals(null) && !stoptime.getText().toString().equals(null) && !score.getText().toString().equals(null) && !standclass.getText().toString().equals(null) && !standproject.getText().toString().equals("")&&d<0&&now<=0) {
                    Intent intent = new Intent(Career_Pulish_Activity.this, Career_Pulish_Second_Activity.class);

                    intent.putExtra("school", school.getText().toString());
                    intent.putExtra("department", department.getText().toString());
                    intent.putExtra("ministry", ministry.getText().toString());
                    intent.putExtra("starttime", starttime.getText().toString());
                    intent.putExtra("stoptime", stoptime.getText().toString());

                    intent.putExtra("score", score.getText().toString());
                    intent.putExtra("standclass", standclass.getText().toString());
                    intent.putExtra("standproject", standproject.getText().toString());


                    SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("school", school.getText().toString());
                    editor.putString("department", department.getText().toString());
                    editor.putString("ministry", ministry.getText().toString());
                    editor.putString("starttime", starttime.getText().toString());
                    editor.putString("stoptime", stoptime.getText().toString());
                    editor.putString("score", score.getText().toString());
                    editor.putString("standclass", standclass.getText().toString());
                    editor.putString("standproject", standproject.getText().toString());
                    editor.commit();//在sharedpreferences中写数据


                    Log.d("school", "学校是" + school.getText().toString());
                    Log.d("department", "系部是" + department.getText().toString());
                    Log.d("ministry", "部门是" + ministry.getText().toString());
                    Log.d("starttime", starttime.getText().toString());
                    Log.d("stoptime", stoptime.getText().toString());

                    Log.d("score", score.getText().toString());
                    Log.d("standclass", standclass.getText().toString());
                    intent.putExtra("standproject", standproject.getText().toString());
                    startActivity(intent);
                } else {
                    if (d>0||d==0||now>0){
                        Toast.makeText(Career_Pulish_Activity.this, "请检查时间是否符合要求哦！", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(Career_Pulish_Activity.this, "请补全空白数据哦！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdata();
            }
        });
        ministry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setministty();
            }
        });
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settime(v.getId());
            }
        });
        stoptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settime(v.getId());
            }
        });
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScore();
            }
        });
        standclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setstandclassdata();
            }
        });
        standproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setstandproject();
            }
        });

    }

    private void getSdata() {

        //在sharedpreferences中读取数据
        SharedPreferences a=getSharedPreferences("data",MODE_PRIVATE);
        school.setText(a.getString("school","四川交通职业技术学院"));
        department.setText(a.getString("department", ""));
        ministry.setText(a.getString("ministry", ""));
        starttime.setText(a.getString("starttime", ""));
        stoptime.setText(a.getString("stoptime", ""));
        score.setText(a.getString("score", ""));
        standclass.setText(a.getString("standclass", ""));
        standproject.setText(a.getString("standproject", ""));

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(Career_Pulish_Activity.this);//弹出选择框
            dialog.setTitle("提示");
            dialog.setMessage("确定退出吗");
            dialog.setPositiveButton("保存至草稿箱", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("school", school.getText().toString());
                    editor.putString("department", department.getText().toString());
                    editor.putString("ministry", ministry.getText().toString());
                    editor.putString("starttime", starttime.getText().toString());
                    editor.putString("stoptime", stoptime.getText().toString());
                    editor.putString("score", score.getText().toString());
                    editor.putString("standclass", standclass.getText().toString());
                    editor.putString("standproject", standproject.getText().toString());
                    editor.commit();//在sharedpreferences中写数据
                    finish();
                }
            });
            dialog.setNegativeButton("放弃编辑", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.clear();
                    editor.commit();
                    finish();
                }
            });
            dialog.show();

        }

        return false;

    }


    private void settime(final int id) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        final Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2037, 01, 01);
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Log.d("ce", "id=" + id);
                String dateString = formatter.format(date);
                TextView edit = (TextView) findViewById(id);
                edit.setText(dateString);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .isCenterLabel(false)

                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();
        pvTime.show();
    }

    private void setScore() {
        Log.d("ce", "列表1" + String.valueOf(options1Items.size()));
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                score.setText(scoreList.get(options1));
            }
        }).isDialog(true).build();
        pvOptions.setPicker(scoreList);
        pvOptions.show();
    }


    private void setdata() {
        Log.d("ce", "列表1" + String.valueOf(options1Items.size()));
        Log.d("ce", "列表1" + String.valueOf(options2Items.size()));
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                option = options1;
                department.setText(options1Items.get(options1));
                ministry.setText(options2Items.get(options1).get(option2));
            }
        }).isDialog(true).build();
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }

    private void setministty() {
        Log.d("ce", "列表1" + String.valueOf(options1Items.size()));
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                ministry.setText(options2Items.get(option).get(options1));
            }
        }).isDialog(true).build();
        pvOptions.setPicker(options2Items.get(option));
        pvOptions.show();
    }

    private void setstandclassdata() {
        Log.d("ce", "列表1" + String.valueOf(standclass1Items.size()));
        Log.d("ce", "列表1" + String.valueOf(standproject2Items.size()));
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                option1 = options1;
                String tx = standclass1Items.get(options1) + standproject2Items.get(options1).get(option2);
                standclass.setText(standclass1Items.get(options1));

            }
        }).isDialog(true).build();
        pvOptions.setPicker(standclass1Items);
        pvOptions.show();
    }

    private void setstandproject() {
        Log.d("ce", "列表1" + String.valueOf(options1Items.size()));
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                standproject.setText(standproject2Items.get(option1).get(options1));
            }
        }).isDialog(true).build();
        pvOptions.setPicker(standproject2Items.get(option1));
        pvOptions.show();
    }


    private void getdata() {
        String url = "http://123.206.92.38/department.html";
        http1.getdepartment(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                try {
                    parseDepartment(respones.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(Career_Pulish_Activity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getstanddata() {
        String url = "http://123.206.92.38/stand.html";
        http1.getdepartment(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) throws JSONException {
                parseStand(respones.toString());
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(Career_Pulish_Activity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void parseStand(String respones) throws JSONException {

        JSONArray jsonArray = null;
        Log.d("ce", "列表" + respones.toString());
        jsonArray = new JSONArray(respones.toString());
        String standclass;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            standclass = jsonObject.getString("standclass");
            Log.d("ce", "列表kaishi " + standclass);
            standclass1Items.add(standclass);
            JSONArray projects = jsonObject.getJSONArray("standproject");
            Log.d("ce", "列表kaishi " + String.valueOf(projects.length()));
            ArrayList<String> options2Items_01 = new ArrayList<>();
            ;
            for (int j = 0; j < projects.length(); j++) {

                options2Items_01.add(projects.get(j).toString());
                Log.d("ce", "列表kaishi " + projects.get(j).toString());
            }
            standproject2Items.add(options2Items_01);

        }

    }


    private void getScoredata() {
        String url = "http://123.206.92.38/score.html";
        http1.getdepartment(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) throws JSONException {
                parseScoredata(respones.toString());
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(Career_Pulish_Activity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void parseDepartment(String respones) throws JSONException {

        JSONArray jsonArray = null;
        Log.d("ce", "列表" + respones.toString());
        jsonArray = new JSONArray(respones.toString());
        String department;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            department = jsonObject.getString("department");
            Log.d("ce", "列表kaishi " + department);
            options1Items.add(department);
            JSONArray ministy = jsonObject.getJSONArray("ministy");
            Log.d("ce", "列表kaishi " + String.valueOf(ministy.length()));
            ArrayList<String> options2Items_01 = new ArrayList<>();
            ;
            for (int j = 0; j < ministy.length(); j++) {

                options2Items_01.add(ministy.get(j).toString());
                Log.d("ce", "列表kaishi " + ministy.get(j).toString());
            }
            options2Items.add(options2Items_01);

        }

    }

    public void parseScoredata(String respones) throws JSONException {

        JSONArray jsonArray = null;
        Log.d("ce", "列表" + respones.toString());
        jsonArray = new JSONArray(respones.toString());
        String score;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            score = jsonObject.getString("score");
            Log.d("ce", "列表kaishi " + department);
            scoreList.add(score);
        }

    }

}

