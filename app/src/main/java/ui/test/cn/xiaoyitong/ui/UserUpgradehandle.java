package ui.test.cn.xiaoyitong.ui;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;

/**
 * Created by John on 2017/4/29.
 */

public class UserUpgradehandle extends Activity {

    private EditText idTv;
    private EditText idcardTv;
    private EditText nameTv;
    private EditText passwordTv;
    private EditText confpasswordTv;
    private Button authenticationBtn;
    private String id;
    private String idcard;
    private String name;
    private String password;
    private String confpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_upgradehandle);

        idTv = (EditText) findViewById(R.id.user_upgrade_id);
        idcardTv = (EditText) findViewById(R.id.user_upgrade_idcard);
        nameTv = (EditText) findViewById(R.id.user_upgrade_name);
        passwordTv = (EditText) findViewById(R.id.user_upgrade_password);
        confpasswordTv = (EditText) findViewById(R.id.user_upgrade_confpassword);
        authenticationBtn = (Button) findViewById(R.id.user_upgrade_authentication);


        authenticationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userUpgradeinit();
                if (id.equals("") || name.equals("") || idcard.equals("") || password.equals("") || confpassword.equals("")){
                    Toast.makeText(UserUpgradehandle.this,"所有信息不能为空！",Toast.LENGTH_SHORT).show();
                } else if (password.equals(confpassword)){
                    String url = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=select_result&student_id=" + id + "&identity_card=" + idcard;
                    String method = "GET";
                    HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = response;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                } else {
                    Toast.makeText(UserUpgradehandle.this,"密码输入不一致！",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if (msg.obj.toString().equals("false")){
                        userUpgradeinit();
                        Toast.makeText(UserUpgradehandle.this,"用户可用",Toast.LENGTH_SHORT).show();
                        SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
                        String user_name=share.getString("user_name","没有登陆");
                        String address = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=insert_new_student&student_id="+id+"&password="+confpassword+"&phone="+user_name+"&identity_card="+idcard+"&real_name="+name+"";
                        String method = "GET";
                        HttpUtilX.sendHttpRequest(address, method, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Message message = new Message();
                                message.what = 2;
                                message.obj = response;
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    } else {
                        Toast.makeText(UserUpgradehandle.this,"用户已存在",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (msg.obj.toString().equals("true")){
                        Toast.makeText(UserUpgradehandle.this,"认证成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserUpgradehandle.this,LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UserUpgradehandle.this,"认证失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private void userUpgradeinit(){
        id = idTv.getText().toString();
        idcard = idcardTv.getText().toString();
        name = nameTv.getText().toString();
        password = passwordTv.getText().toString();
        confpassword = confpasswordTv.getText().toString();
    }
}
