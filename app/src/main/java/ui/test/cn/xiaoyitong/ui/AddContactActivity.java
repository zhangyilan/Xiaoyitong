package ui.test.cn.xiaoyitong.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;

/**
 * Created by YanChunlin on 2017/4/19.
 */
//添加联系人
public class AddContactActivity extends TestActivity{
    private EditText editText;
    private LinearLayout searchedUserLayout;
    private TextView nameText,mTextView;
    private Button searchBtn;
    private ImageView avatar;
    private InputMethodManager inputMethodManager;
    private String toAddUsername;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        mTextView = (TextView) findViewById(R.id.add_list_friends);

        editText = (EditText) findViewById(R.id.edit_note);
        String strAdd = "添加好友";
        mTextView.setText(strAdd);
        String strUserName = "用户名";
        editText.setHint(strUserName);
        searchedUserLayout = (LinearLayout) findViewById(R.id.ll_user);
        nameText = (TextView) findViewById(R.id.name);
        searchBtn = (Button) findViewById(R.id.search);
        avatar = (ImageView) findViewById(R.id.avatar);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 查找contact
     * @param v
     */
    public void searchContact(View v) {
        final String name = editText.getText().toString();
        String saveText = searchBtn.getText().toString();


        if (getString(R.string.button_search).equals(saveText)) {
            toAddUsername = name;
            if(TextUtils.isEmpty(name)) {
                String st = getResources().getString(R.string.Please_enter_a_username);
                startActivity(new Intent(this, AlertDialog.class).putExtra("msg", st));
                return;
            }

            // TODO 从服务器获取此contact,如果不存在提示不存在此用户

            //服务器存在此用户，显示此用户和添加按钮
            searchedUserLayout.setVisibility(View.VISIBLE);
            nameText.setText(toAddUsername);

        }
    }



    public void back(View v) {
        finish();
    }

    public void chat(View view) {
        Intent intent = new Intent(AddContactActivity.this,DetailActivity.class);
        intent.putExtra("name",editText.getText().toString());
        startActivity(intent);
    }
}