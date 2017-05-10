package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;

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
                    contents = new String[5][7];
                    contents = (String[][]) msg.obj;
                    for (int i=0;i<5;i++){
                        for (int j=0;j<7;j++){
                            if (contents[i][j]==null){
                                contents[i][j]="";
                            }
                        }
                    }
                    Log.d("ce", "开始回调"+contents[4][0]);

                    secondAdapter.setContent(contents, 5, 7);
                    detailCource.setAdapter(secondAdapter);
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
        String term = getIntent().getStringExtra("term");
        String URL1 = "http://123.206.92.38:80/SimpleSchool/schooltimetableservlet?opt=get_table&school_year="+year+"&school_term="+term+"&student_id="+id;
        Log.d("aaa", URL1);
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

    /**
     * 初始化
     */
    public void fillStringArray() {
        contents = new String[5][7];
        contents[0][0] = "现代测试技术\nB211";
        contents[1][0] = "微机原理及应用\nE203";
        contents[2][0] = "电磁场理论\nA212";
        contents[3][0] = "传感器电子测量A\nC309";
        contents[4][0] = "";

        contents[0][1] = "数据结构与算法\nB211";
        contents[1][1] = "";
        contents[2][1] = "面向对象程序设计\nA309";
        contents[3][1] = "面向对象程序设计\nA309";
        contents[4][1] = "";

        contents[0][2] = "微机原理及应用\nE203";
        contents[1][2] = "电磁场理论\nA212";
        contents[2][2] = "现代测试技术\nB211";
        contents[3][2] = "";
        contents[4][2] = "";

        contents[0][3] = "面向对象程序设计\nA309";
        contents[1][3] = "传感器电子测量A\nC309";
        contents[2][3] = "";
        contents[3][3] = "";
        contents[4][3] = "sjdkjskds";

        contents[0][4] = "数据结构与算法\nB211";
        contents[1][4] = "微机原理及应用\nE203";
        contents[2][4] = "";
        contents[3][4] = "";
        contents[4][4] = "";

        contents[0][5] = "";
        contents[1][5] = "";
        contents[2][5] = "";
        contents[3][5] = "";
        contents[4][5] = "";

        contents[0][6] = "星期天";
        contents[1][6] = "";
        contents[2][6] = "";
        contents[3][6] = "";
        contents[4][6] = "";
    }
}
