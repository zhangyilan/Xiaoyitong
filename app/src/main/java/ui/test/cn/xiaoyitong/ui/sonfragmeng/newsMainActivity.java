package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.NewsAdapter;
import ui.test.cn.xiaoyitong.entity.news;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.Httputil;

public class newsMainActivity extends SwipeBackActivity {

    private List<news> newsList=new ArrayList<>();
    private NewsAdapter newsAdapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    newsList.addAll((List<news>) msg.obj);
                    Log.d("ce","回调中"+newsList.toString());
                    Log.d("ce","回调中");
                    //rlist= (List<Msglist>) msg.obj;
                    newsAdapter .notifyDataSetChanged();
                    Log.d("ce","回调11");
                    newsAdapter.myListViewClickListener(new NewsAdapter.ListViewClickListener() {
                        @Override
                        public void onRecycleViewClick(View view, int id) {
                            Log.d("id",String.valueOf(id));
                            Intent intent=new Intent(newsMainActivity.this,Content_Activity.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }
                    });
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.news_list);
        sendRequest();

        ImageView back = (ImageView) findViewById(R.id.back);
        TextView biaoti= (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("校园资讯");
        newsAdapter=new NewsAdapter(newsMainActivity.this, R.layout.news_list_item,newsList);
        final ListView listView= (ListView) findViewById(R.id.new_list);
        listView.setAdapter(newsAdapter);

    }

    private void sendRequest() {
        //String url="http://10.126.3.159:8080/newsapp/AppServlet?opt=getdata";
       // String url="http://192.168.1.100:8080/newsapp/AppServlet?opt=getdata";
        String url="http://123.206.92.38/SimpleSchool/AppServlet?opt=getdata";

       //String url="http://192.168.1.101:8080/newsapp/AppServlet?opt=getdata";
        Httputil.sendRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Message mes=new Message();
                mes.what=1;
                mes.obj=respones;
                handler.sendMessage(mes);
                Log.d("ce","回调中");
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
 }
