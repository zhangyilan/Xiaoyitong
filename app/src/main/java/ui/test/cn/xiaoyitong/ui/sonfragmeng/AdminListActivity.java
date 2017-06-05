package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
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
                        public void onRecycleViewClick(View view, int id,String status) {
                            Intent intent=new Intent(AdminListActivity.this,AdminContentActivity.class);
                            intent.putExtra("id",id);
                            intent.putExtra("status",status);
                            startActivity(intent);
                        }
                    });
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career_publish_adminlistlayout);
        sendRequest();

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
