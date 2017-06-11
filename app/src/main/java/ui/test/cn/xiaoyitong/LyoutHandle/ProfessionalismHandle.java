package ui.test.cn.xiaoyitong.LyoutHandle;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.Professionalism;
import ui.test.cn.xiaoyitong.adapter.ProfessionalismAdapter;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http1;


/**
 * Created by John on 2017/5/25.
 */

public class ProfessionalismHandle extends Activity{
    private ListView listView;
    private ProfessionalismAdapter adapter;

    private List<Professionalism> professionalismsList = new ArrayList<>();
    private List<Professionalism> list = new ArrayList<Professionalism>();
    private SwipeRefreshLayout refreshLayout;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    System.out.println("添加数据到UI");
                    professionalismsList.addAll((List<Professionalism>)msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.layout_professionalism_list);
        System.out.println("adsfsdaf");

        System.out.println("adsfsdaf"+String.valueOf(professionalismsList.size()));
        Log.d("aaaaa","fdgsdfg"+String.valueOf(professionalismsList.size()));
        adapter = new ProfessionalismAdapter(ProfessionalismHandle.this, R.layout.item_professionalism,professionalismsList);
        listView = (ListView) findViewById(R.id.layout_professionalism_listview);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_professionalism_refresh);
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        ImageView back = (ImageView) findViewById(R.id.back);
        Button imgmore = (Button) findViewById(R.id.imgmore);
        imgmore.setVisibility(View.INVISIBLE);
        listView.setAdapter(adapter);
        initProfessionalism();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("职业素养");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
                Professionalism professionalism = professionalismsList.get(position);
                String id = professionalism.getId();
                Intent intent = new Intent(ProfessionalismHandle.this,ProfessionalismDetailedHandle.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initProfessionalism();
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
    }
    private void initProfessionalism(){
        String url="http://123.206.92.38:80/SimpleSchool/userJoinServlet?opt=get_school_activity_title";

        http1.sendRequestCustomer(url, new HttpCallBackListener() {
            @Override
            public void onFinish(Object respones) {
                Message mes=new Message();
                mes.what=1;
                mes.obj=respones;
                handler.sendMessage(mes);
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(ProfessionalismHandle.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Date ConverToDate(String strDate) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.parse(strDate);
    }

    private void  addDate(){
        for (int i=0;i<=10;i++){
            Professionalism professionalism = new Professionalism("拉拉","儿童节","2015-03——201506","阿斯蒂芬","12","33","25");
            professionalismsList.add(professionalism);
        }

    }
}
