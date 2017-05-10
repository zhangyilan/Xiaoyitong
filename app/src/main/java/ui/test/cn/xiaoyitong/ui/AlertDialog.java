package ui.test.cn.xiaoyitong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ui.test.cn.xiaoyitong.R;


/**
 * Created by YanChunlin on 2017/4/19.
 */

public class AlertDialog extends TestActivity {
    private TextView mTextView;
    private Button mButton;
    private int position;
    private ImageView imageView;
    private EditText editText;
    private boolean isEditextShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        mTextView = (TextView) findViewById(R.id.title);
        mButton = (Button) findViewById(R.id.btn_cancel);
        imageView = (ImageView) findViewById(R.id.image);
        editText = (EditText) findViewById(R.id.edit);
        //提示内容
        String msg = getIntent().getStringExtra("msg");
        //提示标题
        String title = getIntent().getStringExtra("title");
        position = getIntent().getIntExtra("position", -1);
        //是否显示取消标题
        boolean isCanceTitle=getIntent().getBooleanExtra("titleIsCancel", false);
        //是否显示取消按钮
        boolean isCanceShow = getIntent().getBooleanExtra("cancel", false);
        //是否显示文本编辑框
        isEditextShow = getIntent().getBooleanExtra("editTextShow",false);
        String edit_text = getIntent().getStringExtra("edit_text");

        if(msg != null)
            ((TextView)findViewById(R.id.alert_message)).setText(msg);
        if(title != null)
            mTextView.setText(title);
        if(isCanceTitle){
            mTextView.setVisibility(View.GONE);
        }
        if(isCanceShow)
            mButton.setVisibility(View.VISIBLE);
        if(isEditextShow){
            editText.setVisibility(View.VISIBLE);
            editText.setText(edit_text);
        }
    }

    public void ok(View view){
        setResult(RESULT_OK,new Intent().putExtra("position", position).
                putExtra("edittext", editText.getText().toString()));
        if(position != -1)
            ChatActivity.resendPos = position;
        finish();

    }

    public void cancel(View view){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }
}