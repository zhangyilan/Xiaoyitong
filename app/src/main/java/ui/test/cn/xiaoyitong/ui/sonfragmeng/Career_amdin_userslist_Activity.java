package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.UserAmdinListAdapter;
import ui.test.cn.xiaoyitong.entity.Careerpublish;

/**
 * Created by lenovo on 2017/06/04.
 */

public class Career_amdin_userslist_Activity extends Activity {
    private List<Careerpublish> publishList=new ArrayList<>();
    private UserAmdinListAdapter userAmdinListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career_pulish_userlist_layout);

        Careerpublish careerpublish=new Careerpublish("张三","20150594");

        publishList.add(careerpublish);
        Careerpublish careerpublish1=new Careerpublish("李四","20150594");
        publishList.add(careerpublish1);
        Careerpublish careerpublish2=new Careerpublish("王二","20150594");
        publishList.add(careerpublish2);



        userAmdinListAdapter=new UserAmdinListAdapter(Career_amdin_userslist_Activity.this, R.layout.users_list_item,publishList);
        final ListView listView= (ListView) findViewById(R.id.users_list);
        listView.setAdapter(userAmdinListAdapter);

        userAmdinListAdapter.myListViewClickListener(new UserAmdinListAdapter.ListViewClickListener() {
            @Override
            public void onRecycleViewClick(View view, int id, String name) {
                Toast.makeText(Career_amdin_userslist_Activity.this,name,Toast.LENGTH_SHORT).show();

            }
        });

    }
}
