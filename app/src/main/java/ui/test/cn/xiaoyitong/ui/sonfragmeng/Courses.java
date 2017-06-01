package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.AbsGridAdapter;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http;

/**
 * Created by asus on 2017/4/20.
 */

public class Courses extends SwipeBackActivity {
    private GridView detailCource;
    private String[][] contents;
    private AbsGridAdapter secondAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    if (msg.obj.toString().length()>3) {
                        contents = new String[5][7];
                        contents = (String[][]) msg.obj;
                      //  Log.d("ce","课表信息"+contents[0][]);
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 7; j++) {
                                if (contents[i][j] == null) {
                                    contents[i][j] = "";
                                }
                            }
                        }
                        secondAdapter.setContent(contents, 5, 7);
                        detailCource.setAdapter(secondAdapter);
                    }else {
                        finish();
                        Toast.makeText(Courses.this,"目前暂无您的课表",Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cource);
        contents = new String[5][7];
        //fillStringArray();
        detailCource = (GridView) findViewById(R.id.courceDetail);
        // String URL = "http://123.206.92.38/SimpleSchool/AppServlet?opt=getdata";

        String id = getIntent().getStringExtra("id");
        String year = getIntent().getStringExtra("year");
        Log.d("ce","哈哈"+year);
        String term = getIntent().getStringExtra("term");
        Log.d("ce","哈哈"+term);
        String URL1 = "http://123.206.92.38:80/SimpleSchool/schooltimetableservlet?opt=get_table&school_year="+year+"&school_term="+term+"&student_id="+id;
        http.sendRequest(URL1, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Message message = new Message();
                message.obj = respones;
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        secondAdapter = new AbsGridAdapter(this);
    }

}
