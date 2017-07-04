package ui.test.cn.xiaoyitong.LyoutHandle;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.CloudList;
import ui.test.cn.xiaoyitong.adapter.CloudListAdapter;

/**
 * Created by John on 2017/4/18.
 */

public class CloudRecyclerViewHandle extends SwipeBackActivity {
    private List<CloudList> list = new ArrayList<CloudList>();
    private RecyclerView mRecyclerView;
    private CloudListAdapter cloudListAdapter;
    private Button release;
    private SwipeRefreshLayout downrefresh;
    ImageView back;
     TextView biaoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.layout_cloud_recyclerview);
        biaoti = (TextView) findViewById(R.id.biaoti);
        biaoti.setText("云打印");
        back = (ImageView) findViewById(R.id.back);
        release = (Button) findViewById(R.id.recycler_cloud_release);
        downrefresh = (SwipeRefreshLayout) findViewById(R.id.recycler_cloud_downrefresh);
        downrefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_cloud);

        LinearLayoutManager laoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(laoutManager);
        cloudListAdapter = new CloudListAdapter(list);
        mRecyclerView.setAdapter(cloudListAdapter);
        downrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecycerView();
                sendRequestWithHttpClient();
                downrefresh.setRefreshing(false);
            }
        });

        sendRequestWithHttpClient();
        cloudListAdapter.notifyDataSetChanged();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CloudRecyclerViewHandle.this, CloudReleaseHandle.class);
                startActivity(intent);
            }
        });

        cloudListAdapter.myRecycleViewClickListener(new CloudListAdapter.OnRecycleViewClickListener() {
            @Override
            public void onRecycleViewClick(View view, String expressId) {
                Intent intent = new Intent(CloudRecyclerViewHandle.this, CloudDetailedHandle.class);
                intent.putExtra("expressId", expressId);
                startActivity(intent);
            }
        });
    }

    private void initRecycerView() {
        mRecyclerView.setAdapter(cloudListAdapter);
        mRecyclerView.removeAllViews();
        cloudListAdapter.notifyDataSetChanged();
    }

    private void sendRequestWithHttpClient() {
        final String method = "GET";
        String address = "http://123.206.92.38:80/SimpleSchool/stampservlet?opt=get_stamp";
        String request = HttpUtilX.sendHttpRequest(address, method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    String id = null;
                    String imageUrl = null;
                    String title = null;
                    String serviceTime = null;
                    String price = null;
                    JSONArray jsonArray = new JSONArray(response.toString());
                    list.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        id = jsonobject.getString("id");
                        imageUrl = jsonobject.getString("img");
                        title = jsonobject.getString("stamp_user");
                        serviceTime = jsonobject.getString("stamp_time");
                        price = jsonobject.getString("stamp_price");
                        CloudList cloudList = new CloudList(id, imageUrl, title, serviceTime, price);
                        list.add(cloudList);
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }
}
