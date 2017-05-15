package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.news;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.Httputil;

/**
 * Created by lenovo on 2017/04/15.
 */

public class Content_Activity extends SwipeBackActivity {
    TextView title, time, author, sourse, read, content;
    ImageView img1, img2, img3;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            news news = (ui.test.cn.xiaoyitong.entity.news) msg.obj;
            title.setText(news.getTitle());
            time.setText(news.getPostime());
            author.setText(news.getAuthor());
            sourse.setText(news.getSource());
            read.setText(String.valueOf(news.getRead()));
            content.setText(news.getContent());

            if (news.getSet() != null && !(news.getSet()).isEmpty()) {
                Iterator j = news.getSet().iterator();
                List<Bitmap> list = new ArrayList<>();
                while (j.hasNext()) {//遍历
                    // System.out.println("图片名字"+j.next());
                    list.add((Bitmap) j.next());
                }
                if (list != null && !list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {


                        if (i == 0 && list.get(i) != null) {
                            img1.setVisibility(View.VISIBLE);
                            img1.setImageBitmap(list.get(i));
                        }
                        if (i == 1 && list.get(i) != null) {
                            img2.setVisibility(View.VISIBLE);
                            img2.setImageBitmap(list.get(i));
                        }
                        if (i == 2 && list.get(i) != null) {
                            img3.setVisibility(View.VISIBLE);
                            img3.setImageBitmap(list.get(i));
                        }

                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.news_contents);
        title = (TextView) findViewById(R.id.news_content_title);
        time = (TextView) findViewById(R.id.news_content_time);
        author = (TextView) findViewById(R.id.news_content_author);
        sourse = (TextView) findViewById(R.id.news_content_sourse);
        read = (TextView) findViewById(R.id.news_content_read);
        content = (TextView) findViewById(R.id.news_content_text);
        img1 = (ImageView) findViewById(R.id.news_content_img1);
        img2 = (ImageView) findViewById(R.id.news_content_img2);
        img3 = (ImageView) findViewById(R.id.news_content_img3);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        getcontent(id);

        ImageView back = (ImageView) findViewById(R.id.back);
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("");
    }

    private void getcontent(int id) {
        String url = "http://123.206.92.38/SimpleSchool/AppServlet?opt=getcontent&id=" + id;
        Httputil.sendcontentrequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Message mes = new Message();
                mes.what = 2;
                mes.obj = respones;
                handler.sendMessage(mes);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


}
