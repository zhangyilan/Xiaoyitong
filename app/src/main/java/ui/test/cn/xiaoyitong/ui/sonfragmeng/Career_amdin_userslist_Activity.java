package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.UserAmdinListAdapter;
import ui.test.cn.xiaoyitong.entity.Careerpublish;
import ui.test.cn.xiaoyitong.entity.Users;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http1;

/**
 * Created by lenovo on 2017/06/04.
 */

public class Career_amdin_userslist_Activity extends Activity {
    private List<Users> publishList=new ArrayList<>();
    private UserAmdinListAdapter userAmdinListAdapter;
    Careerpublish careerpublish=new Careerpublish();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    careerpublish = (Careerpublish) msg.obj;

                    publishList.addAll(careerpublish.getUserses());
                    userAmdinListAdapter .notifyDataSetChanged();
                    break;


            }


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career_pulish_userlist_layout);
        Intent intent = getIntent();
         int id = intent.getIntExtra("id", 0);
         getcontent(id);



        userAmdinListAdapter=new UserAmdinListAdapter(Career_amdin_userslist_Activity.this, R.layout.users_list_item,publishList);
        final ListView listView= (ListView) findViewById(R.id.users_list);
        listView.setAdapter(userAmdinListAdapter);

        userAmdinListAdapter.myListViewClickListener(new UserAmdinListAdapter.ListViewClickListener() {
            @Override
            public void onRecycleViewClick(View view,  String name) {
                Toast.makeText(Career_amdin_userslist_Activity.this,name,Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void getcontent(int id) {
        String url = "http://123.206.92.38/SimpleSchool/userJoinServlet?opt=get_school_activity&id=" + id;
        http1.getuserlist(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Message mes = new Message();
                mes.what = 1;
                mes.obj = respones;
                handler.sendMessage(mes);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(Career_amdin_userslist_Activity.this, "网络异常,获取数据失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
