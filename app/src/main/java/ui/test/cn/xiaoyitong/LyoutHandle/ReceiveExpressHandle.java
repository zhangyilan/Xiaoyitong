package ui.test.cn.xiaoyitong.LyoutHandle;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.ReleaseDetails;
import ui.test.cn.xiaoyitong.ui.ChatActivity;
import ui.test.cn.xiaoyitong.ui.DetailActivity;


/**
 * Created by John on 2017/4/15.
 */

public class ReceiveExpressHandle extends SwipeBackActivity {
    private ImageView image;
    private TextView nickName;
    private TextView name;
    private TextView phoneNumber;
    private TextView pickNumber;
    private TextView address;
    private TextView urgent;
    private TextView expectationtime;
    private TextView type;

    private String expressId;

    private List<ReleaseDetails> list=new ArrayList<ReleaseDetails>();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            final ReleaseDetails releaseDetails = (ReleaseDetails) msg.obj;
            Picasso.with(ReceiveExpressHandle.this).load(releaseDetails.getImageId()).into(image);
            nickName.setText(releaseDetails.getNickName());
            name.setText(releaseDetails.getName());
            phoneNumber.setText(releaseDetails.getPhoneNumber());
            pickNumber.setText(releaseDetails.getPickNumber());
            address.setText(releaseDetails.getAddress());
            urgent.setText(releaseDetails.getUrgent());
            expectationtime.setText(releaseDetails.getExpectationtime());
            type.setText(releaseDetails.getType());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.layout_release_express_detailed);
        Intent intent = getIntent();
        expressId = intent.getStringExtra("expressId");
        System.out.println(expressId);
        init();
        sendRequestWithHttpClient();
    }

    private void init() {
        image = (ImageView) findViewById(R.id.layout_release_express_detailed_image);
        nickName = (TextView) findViewById(R.id.layout_release_express_detailed_nickname);
        name = (TextView) findViewById(R.id.layout_release_express_detailed_name);
        phoneNumber = (TextView) findViewById(R.id.layout_release_express_detailed_phonenumber);
        pickNumber = (TextView) findViewById(R.id.layout_release_express_detailed_picknumber);
        address = (TextView) findViewById(R.id.layout_release_express_detailed_address);
        urgent = (TextView) findViewById(R.id.layout_release_express_detailed_urgent);
        expectationtime = (TextView) findViewById(R.id.layout_release_express_detailed_expectationtime);
        type = (TextView) findViewById(R.id.layout_release_express_detailed_type);
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        ImageView  back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("快递发布");
        sendRequestWithHttpClient();
    }

    public void addData(){
        Message msg =new Message();
        ReleaseDetails releaseDetails = new ReleaseDetails("imageUrl","lalla","先开机","13558550320","9527","交院2栋313","特急","05/12 13:30","顺丰快递");
        msg.obj = releaseDetails;
        handler.sendMessage(msg);
    }

    private void sendRequestWithHttpClient(){
        String method = "GET";
        String url = "http://123.206.92.38/SimpleSchool/expressservlet?opt=get_express";
        String requst = HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    String  id = null;
                    String simage = null;
                    String snickName = null;
                    String sname = null;
                    String sphoneNumber = null;
                    String spickNumber = null;
                    String saddress = null;
                    String surgent = null;
                    String sexpectationtime = null;
                    String stype = null;
                    JSONArray jsonArray=new JSONArray(response.toString());
                    for (int  i=0;i<jsonArray.length();i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        id = jsonobject.getString("id");
                        simage = jsonobject.getString("img");
                        snickName = jsonobject.getString("real_name");
                        sname = jsonobject.getString("express_user");
                        sphoneNumber = jsonobject.getString("phone");
                        spickNumber = jsonobject.getString("express_code");
                        saddress = jsonobject.getString("user_address");
                        surgent = jsonobject.getString("emergency");
                        sexpectationtime = jsonobject.getString("emergency");//等待服务器加入字段，先用picknumber代替
                        stype = jsonobject.getString("express_name");
                        if (expressId.equals(id)){
                            Message msg =new Message();
                            ReleaseDetails releaseDetails = new ReleaseDetails(simage,snickName,sname,sphoneNumber,spickNumber,saddress,surgent,sexpectationtime,stype);
                            msg.obj = releaseDetails;
                            handler.sendMessage(msg);
                        }

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void lxchat(View view) {
        String phone = nickName.getText().toString();
        startActivity(new Intent(ReceiveExpressHandle.this, DetailActivity.class).putExtra("name", phone));
    }
}
