package ui.test.cn.xiaoyitong.ui;

import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.ListLazyAdapter;
import ui.test.cn.xiaoyitong.emo.EmojiconEditText;
import ui.test.cn.xiaoyitong.emo.EmojiconTextView;
import ui.test.cn.xiaoyitong.entity.Constants;
import ui.test.cn.xiaoyitong.entity.DateUtils;


/**
 * Created by YanChunlin on 2017/4/25.
 */

public class MoodActivity extends Activity {
    private ImageView back_img;
    private TextView user_name;
    private TextView mood_time;
    private ImageView lc_chat;
    private TextView mood_info;
    private LinearLayout mood_body;
    private LinearLayout mf_comment;
    private ImageView mf_like_icon;
    private LinearLayout mf_like;
    private LinearLayout mood_action;
    private ListView list_view;
    private View edit_mask;
    private EditText edit_tu_cao;
    private Button btn_publish;

    private String toname;
    private String tocontent;
    private String totime;

    //主题的uid和用户名
    private long puid = 123, uid = 456;
    private String pname = "创建人",username = "repeat1";
    //评论列表list
    private List<Map<String, String>> discussList = new ArrayList<Map<String, String>>();

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

        mood_info = (TextView) findViewById(R.id.mood_info);
        tocontent= getIntent().getStringExtra("content");
        mood_info.setText(tocontent);
        mood_body = (LinearLayout) findViewById(R.id.mood_body);
        mf_comment = (LinearLayout) findViewById(R.id.mf_comment);
        mf_like_icon = (ImageView) findViewById(R.id.mf_like_icon);
        mf_like = (LinearLayout) findViewById(R.id.mf_like);
        mood_action = (LinearLayout) findViewById(R.id.mood_action);
        list_view = (ListView) findViewById(R.id.listview);
        edit_mask = findViewById(R.id.edit_mask);
        edit_tu_cao = (EditText) findViewById(R.id.edit_tu_cao);
        btn_publish = (Button) findViewById(R.id.btn_publish);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void send(View view) {
        String content = edit_tu_cao.getText().toString();
        if(content == null || "".equals(content) || Constants.HINT.DISCUSSION.equals(content)){
            this.alertDialog(MoodActivity.this, "Error", "请输入评论内容! ");
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        //给map设置要显示的值
        map.put("distime", DateUtils.formaterDate2YMDHm(new Date(System.currentTimeMillis())));
        map.put("content", content);

        //设置父贴的发帖人信息
        map.put("puid", puid + "");
        map.put("pname", pname);

        //设置自己的信息
        map.put("uid", uid + "");
        map.put("username", username);

        discussList.add(map);

        ListLazyAdapter adapter = new ListLazyAdapter(this, discussList);
        list_view.setAdapter(adapter);
        edit_tu_cao.setText("");
    }
    private void alertDialog(Context context, String title, String message) {
        new android.app.AlertDialog.Builder(context).setIcon(getResources().getDrawable(R.drawable.no_image))
                .setTitle(title)
                .setMessage(message)
                .create().show();
    }
}
