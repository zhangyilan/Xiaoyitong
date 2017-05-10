package ui.test.cn.xiaoyitong.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.emo.EmojiconEditText;
import ui.test.cn.xiaoyitong.emo.EmojiconTextView;


/**
 * Created by YanChunlin on 2017/4/25.
 */

public class MoodActivity extends Activity {
    private ImageView back_img;
    private TextView user_name;
    private TextView mood_time;
    private ImageView lc_chat;
    private EmojiconTextView mood_info;
    private LinearLayout mood_body;
    private TextView mf_comment_num;
    private LinearLayout mf_comment;
    private ImageView mf_like_icon;
    private TextView mf_like_num;
    private LinearLayout mf_like;
    private LinearLayout mood_action;
    private ListView list_view;
    private View edit_mask;
    private EmojiconEditText edit_tu_cao;
    private Button btn_publish;

    private String toname;
    private String tocontent;
    private String totime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        back_img = (ImageView) findViewById(R.id.back_img);
        user_name = (TextView) findViewById(R.id.user_name);
        toname= getIntent().getStringExtra("username");
        user_name.setText(toname);
        mood_time = (TextView) findViewById(R.id.mood_time);
        totime= getIntent().getStringExtra("time");
        mood_time.setText(totime);
        lc_chat = (ImageView) findViewById(R.id.lc_chat);
        mood_info = (EmojiconTextView) findViewById(R.id.mood_info);
        tocontent= getIntent().getStringExtra("content");
        mood_info.setText(tocontent);
        mood_body = (LinearLayout) findViewById(R.id.mood_body);
        mf_comment_num = (TextView) findViewById(R.id.mf_comment_num);
        mf_comment = (LinearLayout) findViewById(R.id.mf_comment);
        mf_like_icon = (ImageView) findViewById(R.id.mf_like_icon);
        mf_like_num = (TextView) findViewById(R.id.mf_like_num);
        mf_like = (LinearLayout) findViewById(R.id.mf_like);
        mood_action = (LinearLayout) findViewById(R.id.mood_action);
        list_view = (ListView) findViewById(R.id.listview);
        edit_mask = findViewById(R.id.edit_mask);
        edit_tu_cao = (EmojiconEditText) findViewById(R.id.edit_tu_cao);
        btn_publish = (Button) findViewById(R.id.btn_publish);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
