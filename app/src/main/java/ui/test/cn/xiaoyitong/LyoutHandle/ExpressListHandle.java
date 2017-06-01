package ui.test.cn.xiaoyitong.LyoutHandle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.ExpressList;
import ui.test.cn.xiaoyitong.adapter.ExpressListAdapter;
import ui.test.cn.xiaoyitong.controller.HXSDKHelper;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallback;
import ui.test.cn.xiaoyitong.ui.BeasActivity;
import ui.test.cn.xiaoyitong.ui.FirstActivity;
import ui.test.cn.xiaoyitong.ui.LoginActivity;
import ui.test.cn.xiaoyitong.ui.UserUpgradehandle;
import ui.test.cn.xiaoyitong.utils.HttpUtil;


/**
 * Created by John on 2017/4/14.
 */

public class ExpressListHandle extends SwipeBackActivity {
    private List<ExpressList> list = new ArrayList<ExpressList>();
    private RecyclerView mRecyclerView;
    private ExpressListAdapter expressListAdapter;
    private Button release;
    private SwipeRefreshLayout downrefresh;
    private String userId;
    private String orderExpressId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.layout_release_express_recycelview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_express);
        release = (Button) findViewById(R.id.recycler_express_release);
        downrefresh = (SwipeRefreshLayout) findViewById(R.id.recycler_express_downrefresh);
        LinearLayoutManager laoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(laoutManager);
        expressListAdapter = new ExpressListAdapter(list);
        mRecyclerView.setAdapter(expressListAdapter);



        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
                String user_name=share.getString("user_name","没有登陆");
                Log.d("user_name",user_name);
                if (user_name.equals("没有登陆")){
                    Toast.makeText(ExpressListHandle.this,"您还未登陆,请登陆",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ExpressListHandle.this, LoginActivity.class));
                }
//                if (!HXSDKHelper.getInstance().isLogined()) {
//                    Toast.makeText(ExpressListHandle.this,"您还未登陆,请登陆",Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ExpressListHandle.this, LoginActivity.class));
//                }
                else {
                    String url = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=get_formal&user=" + user_name + "";
                    HttpUtil httpUtil = new HttpUtil();
                    if (httpUtil.isNetworkAvailable(ExpressListHandle.this)) {
                        httpUtil.getData(url, new HttpCallback() {
                            @Override
                            public void onFinish(String respose) {
                                Message message = new Message();
                                message.what=1;
                                message.obj=respose;
                                handler.sendMessage(message);
                            }
                            @Override
                            public void onerror(Exception e) {
                            }
                        });
                    }
                }
            }
        });

        downrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecycerView();
                sendRequestWithHttpClient();
                downrefresh.setRefreshing(false);
            }
        });
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        ImageView  back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("快递收发");
        sendRequestWithHttpClient();

        //界面跳转
        expressListAdapter.myRecycleViewClickListener(new ExpressListAdapter.OnRecycleViewClickListener() {
            @Override
            public void onRecycleViewClick(View view, final String expressId,final String nickNumber) {
                userId = nickNumber;
                orderExpressId =expressId;
                new AlertDialog.Builder(ExpressListHandle.this).setTitle("是否接单！").setMessage("为保护用户隐私，接单后将无法放弃！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
                                String user_name=share.getString("user_name","没有登陆");
                                Log.d("user_name",user_name);
                                if (user_name.equals("没有登陆")){
                                    Toast.makeText(ExpressListHandle.this,"您还未登陆,请登陆",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ExpressListHandle.this, LoginActivity.class));
                                }
//                                if (!HXSDKHelper.getInstance().isLogined()) {
//                                    Toast.makeText(ExpressListHandle.this,"您还未登陆,请登陆",Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(ExpressListHandle.this, LoginActivity.class));
//                                }
                                else {
                                    String url = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=get_formal&user=" + user_name + "";
                                    HttpUtil httpUtil = new HttpUtil();
                                    if (httpUtil.isNetworkAvailable(ExpressListHandle.this)) {
                                        httpUtil.getData(url, new HttpCallback() {
                                            @Override
                                            public void onFinish(String respose) {
                                                Message message = new Message();
                                                message.obj=respose;
                                                message.what=0;
                                                handler.sendMessage(message);
                                            }
                                            @Override
                                            public void onerror(Exception e) {
                                            }
                                        });
                                    }
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }
        });
    }

    private void initRecycerView(){
        mRecyclerView.setAdapter(expressListAdapter);
        mRecyclerView.removeAllViews();
        expressListAdapter.notifyDataSetChanged();
    }

    private void submitOrder(String address){
        final String method = "GET";
        HttpUtilX.sendHttpRequest(address, method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

            }
            @Override
            public void onError(Exception e) {
            }
        });
    }


    private void sendRequestWithHttpClient() {
        final String method = "GET";
                String address = "http://123.206.92.38/SimpleSchool/expressservlet?opt=get_express";
                String request = HttpUtilX.sendHttpRequest(address,method, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        try{
                            System.out.println("开始数据解析");
                            String imageurl = null;
                            String  id = null;
                            String  name = null;
                            String  sprice = null;
                            String  format = null;
                            String nickNumber = null;
                            JSONArray jsonArray=new JSONArray(response.toString());
                            list.clear();
                            for (int  i=0;i<jsonArray.length();i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                id = jsonobject.getString("id");
                                name=jsonobject.getString("express_name");
                                sprice = jsonobject.getString("express_price");
                                format = jsonobject.getString("express_format");
                                imageurl = jsonobject.getString("img");
                                nickNumber = jsonobject.getString("express_user");
                                //String imgUrll="http://119.29.114.210:8080/mybookshop/"+imgurl;
                                System.out.println("数据解析完成" + i + id + name + sprice + format + imageurl);
                                ExpressList gooditem=new ExpressList(imageurl,id,name,sprice,format,nickNumber);
                                list.add(gooditem);
                            }
                        }catch (Exception e){
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println("数据解析失败 " + e);
                    }
                });
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if (msg.obj.equals("true")){
                        validateBusiness();

                    }else {
                        new AlertDialog.Builder(ExpressListHandle.this).setTitle("您不是正式用户！").setMessage("是否升级为正式用户！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(ExpressListHandle.this,"跳转中",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ExpressListHandle.this,UserUpgradehandle.class);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                    }
                    break;
                case 1:
                    if (msg.obj.equals("true")){
                        final String method = "GET";
                        SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
                        String user_name=share.getString("user_name","没有登陆");
                        String address = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=is_business&user="+user_name;
                        HttpUtilX.sendHttpRequest(address, method, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Message message = new Message();
                                message.obj=response;
                                message.what=3;
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    }else {
                        new AlertDialog.Builder(ExpressListHandle.this).setTitle("您不是正式用户！").setMessage("是否升级为正式用户！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(ExpressListHandle.this,"跳转中",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ExpressListHandle.this,UserUpgradehandle.class);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                    }
                    break;
                case 2:
                    if (msg.obj.equals("true")){
                        //获取商户名
                        SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
                        String user_name=share.getString("user_name","没有登陆");
                        //获取系统时间

                        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                        String data = format.format(new java.util.Date());
                        submitOrder("http://123.206.92.38:80/SimpleSchool/ordersservlet?opt=insert_order&business="+user_name+"&price=5&client="+userId+"&publish_time="+data+"&type=1");
                        submitOrder("http://123.206.92.38/SimpleSchool/expressservlet?opt=update_Express&id=" + msg.obj.toString());
                        Intent intent = new Intent(ExpressListHandle.this, ReceiveExpressHandle.class);
                        intent.putExtra("expressId", orderExpressId);//我草你妈
                        startActivity(intent);
                    } else {
                        Toast.makeText(ExpressListHandle.this,"您不是商户，无法接单！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3://发布快递的商户验证
                    if (msg.obj.equals("true")){
                        Toast.makeText(ExpressListHandle.this,"您是商户，无法发布订单！",Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(ExpressListHandle.this, ExpressDetailedHandle.class));
                    }
                default:
                    break;
            }
        }
    };
    public void validateBusiness(){
        final String method = "GET";
        SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
        String user_name=share.getString("user_name","没有登陆");
        String address = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=is_business&user="+user_name;
        HttpUtilX.sendHttpRequest(address, method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message = new Message();
                message.obj=response;
                message.what=2;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
