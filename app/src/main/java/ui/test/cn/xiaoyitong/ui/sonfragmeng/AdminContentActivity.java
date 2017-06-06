package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Careerpublish;
import ui.test.cn.xiaoyitong.entity.Users;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http1;

/**
 * Created by lenovo on 2017/06/04.
 */

public class AdminContentActivity extends Activity {
    ImageView imageView;
    TextView title, status, score, usercount, activity_time, adress, project, department, background, content;
    LinearLayout usercount_linearLayout, buttonlayout;
    List<Users> userseslist=new ArrayList<Users>();
    Careerpublish careerpublish=new Careerpublish();
    Button sign_in;
    int s = 1;
    String statuss;
    String usercountstring;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    careerpublish = (Careerpublish) msg.obj;

                    imageView.setImageBitmap(careerpublish.getImgBitmap());
                    title.setText(careerpublish.getTheme());
                    score.setText(careerpublish.getQuality_frade());
                   // usercount.setText(careerpublish.getSource());
                    activity_time.setText(careerpublish.getStart_time() + "--" + careerpublish.getEnd_time());
                    adress.setText(careerpublish.getActivity_address());
                    project.setText(careerpublish.getActivity_type());
                    department.setText(careerpublish.getPublish_branch());
                    background.setText(careerpublish.getActivity_background());
                    content.setText(careerpublish.getInclude());
                    userseslist=careerpublish.getUserses();
                    break;
                case 1:
                    String m = (String) msg.obj;
                    Log.d("ce", "返回" + m);
                    if (m.equals("true")) {
                        Toast.makeText(AdminContentActivity.this, "正在签到", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    String ss = (String) msg.obj;
                    Log.d("ce", "返回" + s);
                    if (ss.equals("true")) {
                        Toast.makeText(AdminContentActivity.this, "正在签到中", Toast.LENGTH_SHORT).show();
                        sign_in.setText("结束");
                        s = 2;
                    } else if (ss.equals("false")) {
                        Toast.makeText(AdminContentActivity.this, "允许开始签到", Toast.LENGTH_SHORT).show();
                        sign_in.setText("开始签到");
                        s = 1;
                    }
                    break;

                case 4:
                    String stop = (String) msg.obj;
                    Log.d("ce", "返回" + s);
                    if (stop.equals("false")) {
                        Toast.makeText(AdminContentActivity.this, "正在结束签到", Toast.LENGTH_SHORT).show();
                    }

                    break;


            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_publish_content_layout);
        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 0);
        statuss = intent.getStringExtra("status");
        usercountstring = intent.getStringExtra("usercount");

        imageView = (ImageView) findViewById(R.id.professionalism_detailed_img);
        title = (TextView) findViewById(R.id.professionalism_detailed_title);
        status = (TextView) findViewById(R.id.professionalism_detailed_status);
        score = (TextView) findViewById(R.id.professionalism_detailed_value);
        usercount = (TextView) findViewById(R.id.professionalism_detailed_usercount);
        activity_time = (TextView) findViewById(R.id.professionalism_detailed_time);
        adress = (TextView) findViewById(R.id.professionalism_detailed_address);
        project = (TextView) findViewById(R.id.professionalism_detailed_class);
        department = (TextView) findViewById(R.id.professionalism_detailed_department);
        background = (TextView) findViewById(R.id.professionalism_detailed_activitybg);
        content = (TextView) findViewById(R.id.professionalism_detailed_content);

        sign_in = (Button) findViewById(R.id.admin_sign_in);

        usercount.setText(usercountstring);

        if (statuss.equals("已结束")) {
            sign_in.setText("活动结束");
            sign_in.setEnabled(false);
            sign_in.setBackgroundColor(Color.parseColor("#adadad"));
            s = 0;
        }
        if (statuss.equals("未开始")) {
            sign_in.setText("活动还未开始");
            sign_in.setEnabled(false);
            sign_in.setBackgroundColor(Color.parseColor("#adadad"));
            s = 0;
        }

        if (statuss.equals("进行中") && s != 1) {
            sign_in.setText("结束");
            sign_in.setBackgroundColor(Color.parseColor("#00badc"));
            sign_in.setEnabled(true);
            Log.d("ce", "s=2");
            s = 2;
        }

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ce", "状态" + statuss);

                if (s == 1 && statuss.equals("进行中")) {
                    sign_in.setText("结束");
                    s = 2;

                    status.setText(statuss);
                    String address = "http://123.206.92.38/SimpleSchool/userJoinServlet?opt=set_permit_sign_in";
                    http1.getStatus(address, new HttpCallBackListener() {
                        @Override
                        public void onFinish(Object respones) throws JSONException {
                            Log.d("ce", "回调返回" + respones.toString());
                            Message message = new Message();
                            message.what = 1;
                            message.obj = respones.toString();
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(AdminContentActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else if (s == 2 && statuss.equals("进行中")) {
                    sign_in.setText("开始签到");
                    s = 1;

                    String address = "http://123.206.92.38/SimpleSchool/userJoinServlet?opt=set_not_sign_in";
                    http1.getStatus(address, new HttpCallBackListener() {
                        @Override
                        public void onFinish(Object respones) throws JSONException {
                            Log.d("ce", "回调返回" + respones.toString());
                            Message message = new Message();
                            message.what = 4;
                            message.obj = respones.toString();
                            handler.sendMessage(message);

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(AdminContentActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                if (s == 0 && statuss.equals("已结束")) {
                    Toast.makeText(AdminContentActivity.this, "活动已结束", Toast.LENGTH_SHORT).show();
                }
                if (s == 0 && statuss.equals("未开始")) {
                    Toast.makeText(AdminContentActivity.this, "活动未开始", Toast.LENGTH_SHORT).show();
                }
            }
        });

        status.setText(statuss);

        usercount_linearLayout = (LinearLayout) findViewById(R.id.usercount_activty);

        usercount_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ce","获取人数"+usercount.getText().toString()+"dd");
              if(Integer.valueOf(usercountstring)>0 ) {
                  Toast.makeText(AdminContentActivity.this, "参加活动列表", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(AdminContentActivity.this, Career_amdin_userslist_Activity.class);
                  intent.putExtra("id", id);
                  startActivity(intent);
              } else {
                  Toast.makeText(AdminContentActivity.this, "暂时没人参加", Toast.LENGTH_SHORT).show();

              }
            }
        });
        getcontent(id);
        if (statuss.equals("进行中")) {
            String url = "http://123.206.92.38/SimpleSchool/userJoinServlet?opt=get_sign_in";
            http1.getStatus(url, new HttpCallBackListener() {
                @Override
                public void onFinish(Object respones) throws JSONException {
                    Log.e("tag", respones.toString());
                    Message mes = new Message();
                    mes.what = 3;
                    mes.obj = respones;
                    handler.sendMessage(mes);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

    }


    private void getcontent(int id) {
        String url = "http://123.206.92.38/SimpleSchool/userJoinServlet?opt=get_school_activity&id=" + id;
        http1.getadmindetail(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Message mes = new Message();
                mes.what = 2;
                mes.obj = respones;
                handler.sendMessage(mes);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(AdminContentActivity.this, "网络异常,获取数据失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
