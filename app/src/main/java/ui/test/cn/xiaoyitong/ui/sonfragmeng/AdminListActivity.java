package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.PublishAdapter;
import ui.test.cn.xiaoyitong.entity.Careerpublish;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http1;

/**
 * Created by lenovo on 2017/06/03.
 */

public class AdminListActivity extends Activity {
    private List<Careerpublish> publishList=new ArrayList<>();
    private PublishAdapter publishAdapter;
    private  ImageView serch,back;
    private EditText serchid;
    private Button publish;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    publishList.addAll((List<Careerpublish>) msg.obj);
                    publishAdapter .notifyDataSetChanged();
                    publishAdapter.myListViewClickListener(new PublishAdapter.ListViewClickListener() {
                        @Override
                        public void onRecycleViewClick(View view, int id,String status,String usercount) {
                            Intent intent=new Intent(AdminListActivity.this,AdminContentActivity.class);
                            intent.putExtra("id",id);
                            intent.putExtra("status",status);
                            intent.putExtra("usercount",usercount);
                            startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    publishList.clear();
                    publishList.addAll((List<Careerpublish>) msg.obj);
                    publishAdapter .notifyDataSetChanged();
                    publishAdapter.myListViewClickListener(new PublishAdapter.ListViewClickListener() {
                        @Override
                        public void onRecycleViewClick(View view, int id,String status,String usercount) {
                            Intent intent=new Intent(AdminListActivity.this,AdminContentActivity.class);
                            intent.putExtra("id",id);
                            intent.putExtra("status",status);
                            intent.putExtra("usercount",usercount);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);;
        }
        setContentView(R.layout.career_publish_adminlistlayout);
        sendRequest();
        serch= (ImageView) findViewById(R.id.search_button);
        serchid= (EditText) findViewById(R.id.search_edit);
        back= (ImageView) findViewById(R.id.fh_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

            serch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!serchid.getText().toString().equals("")) {
                        Intent intent=new Intent(AdminListActivity.this,SerchListActivity.class);
                        intent.putExtra("serch",serchid.getText().toString());
                        startActivity(intent);
                    }
                }
            });

        publishAdapter=new PublishAdapter(AdminListActivity.this, R.layout.careerpublish_admin_list_item,publishList);
        final ListView listView= (ListView) findViewById(R.id.new_list);
        listView.setAdapter(publishAdapter);
        publish= (Button) findViewById(R.id.publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminListActivity.this, Career_Pulish_Activity.class);
                startActivity(intent);
            }
        });

    }

    private void getuserActivity(String s) {
        String url="http://123.206.92.38/SimpleSchool/userJoinServlet?opt=get_student_activity&student_id="+s;

        http1.getserch(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Message mes=new Message();
                mes.what=2;
                mes.obj=respones;
                handler.sendMessage(mes);
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(AdminListActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendRequest() {
        String url="http://123.206.92.38/SimpleSchool/userJoinServlet?opt=get_school_activity_title";


        http1.sendRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Message mes=new Message();
                mes.what=1;
                mes.obj=respones;
                handler.sendMessage(mes);
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(AdminListActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
