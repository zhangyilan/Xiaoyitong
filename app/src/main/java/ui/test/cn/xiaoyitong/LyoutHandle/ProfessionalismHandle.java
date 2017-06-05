package ui.test.cn.xiaoyitong.LyoutHandle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.Professionalism;
import ui.test.cn.xiaoyitong.adapter.ProfessionalismAdapter;


/**
 * Created by John on 2017/5/25.
 */

public class ProfessionalismHandle extends Activity{
    private ListView listView;
    private ProfessionalismAdapter adapter;

    private List<Professionalism> professionalismsList = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(){
        public void handlerMessage(Message msg){
                switch (msg.what){
                    case 1:

                }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_professionalism_list);
        System.out.println("adsfsdaf");
        initProfessionalism();
        System.out.println("adsfsdaf"+String.valueOf(professionalismsList.size()));
        Log.d("aaaaa","fdgsdfg"+String.valueOf(professionalismsList.size()));
        adapter = new ProfessionalismAdapter(ProfessionalismHandle.this,R.layout.item_professionalism,professionalismsList);
        listView = (ListView) findViewById(R.id.layout_professionalism_listview);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_professionalism_refresh);
        listView.setAdapter(adapter);
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
                refreshLayout.setRefreshing(false);
            }
        });
    }
    private void initProfessionalism(){
        String url = "http://123.206.92.38:80/SimpleSchool/userJoinServlet?opt=get_school_activity_title";
        String method = "GET";
        Log.d("aaaaa","测");
        String data = HttpUtilX.sendHttpRequest(url,method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                JSONObject jsonobject = null;
                try {
                    String imgUrl = null;
                    String title = null;
                    String department = null;
                    String endTime = null;
                    String startTime = null;
                    String score = null;
                    String id = null;
                    String status = null;
                    JSONArray jsonArray=new JSONArray(response.toString());
                    professionalismsList.clear();
                    for (int  i=0;i<jsonArray.length();i++) {
                        jsonobject = jsonArray.getJSONObject(i);
                        imgUrl = jsonobject.getString("activity_img");
                        title=jsonobject.getString("theme");
                        department = jsonobject.getString("publish_branch");
                        endTime = jsonobject.getString("end_time");
                        startTime = jsonobject.getString("start_time");
                        score = jsonobject.getString("quality_frade");
                        id = (jsonobject.getString("id"));
                        status = jsonobject.getString("state");
                        System.out.println("解析数据成功:" + imgUrl + title + department + endTime + score + id);
                        System.out.println("解析数据成功:"+id);
                        Professionalism professionalism = new Professionalism(imgUrl,title,department,startTime+"——"+endTime,score,id,status);
                        professionalismsList.add(professionalism);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e){

                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public static Date ConverToDate(String strDate) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.parse(strDate);
    }
}
