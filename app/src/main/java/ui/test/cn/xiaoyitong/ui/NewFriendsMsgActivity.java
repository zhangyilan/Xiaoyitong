package ui.test.cn.xiaoyitong.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.NewFriendsMsgAdapter;
import ui.test.cn.xiaoyitong.dao.InviteMessgeDao;
import ui.test.cn.xiaoyitong.entity.Constant;
import ui.test.cn.xiaoyitong.entity.InviteMessage;

/**
 * Created by YanChunlin on 2017/4/19.
 */

public class NewFriendsMsgActivity extends TestActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);

        listView = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        //设置adapter
        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        MyApplication.getInstance().getContactList().get(Constant.NEW_FRIENDS_USERNAME).setUnreadMsgCount(0);

    }

    public void back(View view) {
        finish();
    }


}
