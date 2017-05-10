package ui.test.cn.xiaoyitong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.ContactAdapter;
import ui.test.cn.xiaoyitong.entity.Constant;
import ui.test.cn.xiaoyitong.entity.User;
import ui.test.cn.xiaoyitong.utils.Sidebar;

/**
 * Created by YanChunlin on 2017/4/19.
 */

public class PickContactNoCheckboxActivity extends TestActivity {

    private ListView listView;
    private Sidebar sidebar;
    protected ContactAdapter contactAdapter;
    private List<User> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact_no_checkbox);
        listView = (ListView) findViewById(R.id.list);
        sidebar = (Sidebar) findViewById(R.id.sidebar);
        sidebar.setListView(listView);
        contactList = new ArrayList<User>();
        // 获取设置contactlist
        getContactList();
        // 设置adapter
        contactAdapter = new ContactAdapter(this, R.layout.row_contact, contactList);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(position);
            }
        });

    }

    protected void onListItemClick(int position) {
        setResult(RESULT_OK, new Intent().putExtra("username", contactAdapter.getItem(position)
                .getUsername()));
        finish();
    }

    public void back(View view) {
        finish();
    }

    private void getContactList() {
        contactList.clear();
        Map<String, User> users = MyApplication.getInstance().getContactList();
        Iterator<Map.Entry<String, User>> iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, User> entry = iterator.next();
            if (!entry.getKey().equals(Constant.NEW_FRIENDS_USERNAME) && !entry.getKey().equals(Constant.GROUP_USERNAME) && !entry.getKey().equals(Constant.CHAT_ROOM))
                contactList.add(entry.getValue());
        }
        // 排序
        Collections.sort(contactList, new Comparator<User>() {

            @Override
            public int compare(User lhs, User rhs) {
                return lhs.getUsername().compareTo(rhs.getUsername());
            }
        });
    }

}
