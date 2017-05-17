package ui.test.cn.xiaoyitong.ui.sonfragmeng;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tencent.map.geolocation.TencentLocationManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.OrderInformationAdapter;
import ui.test.cn.xiaoyitong.adapter.OrderList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabLayoutFragment extends Fragment {
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout downwardRefresh;
    private OrderInformationAdapter orderInformationAdapter;
    private List<OrderList> list = new ArrayList<OrderList>();

    private Button daohang, dingwei;
    private int type;
    private TencentLocationManager mLocationManager;
    String aa;

    public static TabLayoutFragment newInstance(int type) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_order_information, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_order_information);
        downwardRefresh = (SwipeRefreshLayout) view.findViewById(R.id.recyclerview_order_downward_refresh);
        downwardRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        // 创建一个线性布局管理器

        LinearLayoutManager laoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(laoutManager);
        orderInformationAdapter = new OrderInformationAdapter(list);
        mRecyclerView.setAdapter(orderInformationAdapter);
        initView();
        return view;
    }

    protected void initView() {
        SharedPreferences share = MyApplication.getSsharedPreferences();
        String user_name=share.getString("user_name","king");
        switch (type) {
            case 0://所有订单信息
                addData("http://123.206.92.38:80/SimpleSchool/ordersservlet?opt=business_get_orders&business="+user_name,"全部");
                break;
            case 1://待支付
                addData("http://123.206.92.38:80/SimpleSchool/ordersservlet?opt=not_get_goods&client="+user_name,"未完成");
                break;
            case 2://待收货
                //addData("http://123.206.92.38:80/SimpleSchool/ordersservlet?opt=not_get_goods&client="+user_name,"待收货");
                break;
            default:
                break;
        }
    }

    private void addData(final String address,final String status){
        sendRequestWithHttpClient(address,status);
        downwardRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecycerView();
                sendRequestWithHttpClient(address,status);
                downwardRefresh.setRefreshing(false);
            }
        });
    }

    private void sendRequestWithHttpClient(String address,final String status){
        String method = "GET";
        HttpUtilX.sendHttpRequest(address, method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    String imageUrl = null;
                    String type = null;
                    String time = null;
                    String id = null;
                    JSONArray jsonArray=new JSONArray(response.toString());
                    list.clear();
                    for (int  i=0;i<jsonArray.length();i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        type=jsonobject.getString("type");
                        time = jsonobject.getString("publish_time");
                        id = jsonobject.getString("order_number");
                        if ("1".equals(type)){
                            type = "快递";
                        } else if ("2".equals(type)) {
                            type = "云打印";
                        } else {
                            type = "其他";
                        }
                        OrderList gooditem=new OrderList("imageUrl",type,time,status,id);
                        list.add(gooditem);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void initRecycerView(){
        mRecyclerView.setAdapter(orderInformationAdapter);
        mRecyclerView.removeAllViews();
        orderInformationAdapter.notifyDataSetChanged();
    }

}