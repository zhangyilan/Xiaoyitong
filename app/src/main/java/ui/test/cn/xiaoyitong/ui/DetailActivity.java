package ui.test.cn.xiaoyitong.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;


/**
 * Created by YanChunlin on 2017/5/11.
 */

public class DetailActivity extends Activity {

    private TextView contact_top;
    private String Username;
    private ProgressDialog progressDialog;
    private String toAddUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_details);

        contact_top = (TextView) findViewById(R.id.contact_top);
        Username = getIntent().getStringExtra("name");
        contact_top.setText(Username);
        toAddUsername = contact_top.getText().toString();
    }
    public void back(View v) {
        finish();
    }
    //聊天
    public void startChat(View view) {
        if(MyApplication.getInstance().getUserName().equals(contact_top.getText().toString())){
            startActivity(new Intent(this, AlertDialog.class).putExtra("msg", "亲！不能和自己聊天哦！"));
            return;
        } else {
            Intent intent=new Intent(DetailActivity.this,ChatActivity.class);
            intent.putExtra("userId",contact_top.getText().toString());
            startActivity(intent);
        }
    }
    //添加好友
    public void addFriends(View view) {
        if(MyApplication.getInstance().getUserName().equals(contact_top.getText().toString())){
            String str = getString(R.string.not_add_myself);
            startActivity(new Intent(this, AlertDialog.class).putExtra("msg", str));
            return;
        }

        if(MyApplication.getInstance().getContactList().containsKey(contact_top.getText().toString())){
            //提示已在好友列表中，无需添加
            if(EMContactManager.getInstance().getBlackListUsernames().contains(contact_top.getText().toString())){
                startActivity(new Intent(this, AlertDialog.class).putExtra("msg", "此用户已是你好友(被拉黑状态)，从黑名单列表中移出即可"));
                return;
            }
            String strin = getString(R.string.This_user_is_already_your_friend);
            startActivity(new Intent(this, AlertDialog.class).putExtra("msg", strin));
            return;
        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo写死了个reason，实际应该让用户手动填入
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMContactManager.getInstance().addContact(toAddUsername, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
