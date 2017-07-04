package ui.test.cn.xiaoyitong.LyoutHandle;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.CloudDetailedList;

/**
 * Created by John on 2017/4/19.
 */

public class CloudDetailedHandle extends Activity {
    private ImageView cDetailedImage;
    private TextView cDetailedTitle;
    private TextView cDetailedNickName;
    private TextView cDetailedPhoneNumber;
    private TextView cDetailedPaginalNumber;
    private TextView cDetailedAddress;
    private TextView cDetailedType;
    private TextView cDetailedTime;
    private TextView cDetailedColor;
    private TextView cDetailedUnivalent;
    private TextView cDetailedTotal;
    private String expressId;
    private ImageView back;
    private TextView biaoti;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final CloudDetailedList cloudDetailedList = (CloudDetailedList) msg.obj;
            Picasso.with(CloudDetailedHandle.this).load(cloudDetailedList.getImageUrl()).into(cDetailedImage);
            cDetailedTitle.setText(cloudDetailedList.getTitle());
            cDetailedNickName.setText(cloudDetailedList.getNickname());
            cDetailedPhoneNumber.setText(cloudDetailedList.getPhoneNumber());
            cDetailedPaginalNumber.setText(cloudDetailedList.getPaginalNumber());
            cDetailedAddress.setText(cloudDetailedList.getAddress());
            cDetailedType.setText(cloudDetailedList.getType());
            cDetailedTime.setText(cloudDetailedList.getServicetime());
            cDetailedColor.setText(cloudDetailedList.getColor());
            cDetailedUnivalent.setText(cloudDetailedList.getUnivalent());
            cDetailedTotal.setText(cloudDetailedList.getTotal());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.layout_cloud_detailed);
        Intent intent = getIntent();
        expressId = intent.getStringExtra("expressId");
        System.out.println("id" + expressId);
        init();
        addData();
    }

    private void addData() {
        String url = "http://123.206.92.38:80/SimpleSchool/stampservlet?opt=get_stamp";
        String method = "GET";

        String request = HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {

            @Override
            public void onFinish(String response) {
                try {
                    System.out.println("开始数据解析" + response);
                    String id = null;
                    String imageUrl = null;
                    String title = null;
                    String nickName = null;
                    String phoneNumber = null;
                    String paginalNumber = null;
                    String address = null;
                    String type = null;
                    String time = null;
                    String color = null;
                    String univalent = null;
                    String total = null;
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        id = jsonobject.getString("id");
                        imageUrl = jsonobject.getString("img");
                        title = jsonobject.getString("id");
                        nickName = jsonobject.getString("real_name");
                        phoneNumber = jsonobject.getString("user_phone");
                        paginalNumber = jsonobject.getString("stamp_pages");
                        address = jsonobject.getString("user_address");
                        type = jsonobject.getString("single_and_double");
                        time = jsonobject.getString("stamp_time");
                        color = jsonobject.getString("color");
                        univalent = jsonobject.getString("stamp_price");
                        if (expressId.equals(id)) {
                            Message msg = new Message();
                            CloudDetailedList cloudDetailedList = new CloudDetailedList(imageUrl, title, nickName, phoneNumber, paginalNumber, address, type, time, color, univalent, total);
                            msg.obj = cloudDetailedList;
                            handler.sendMessage(msg);
                        }
                    }


                } catch (Exception e) {
                    Toast.makeText(CloudDetailedHandle.this, "接收数据失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    private void init() {
        cDetailedImage = (ImageView) findViewById(R.id.layout_cloud_detailed_image);
        cDetailedTitle = (TextView) findViewById(R.id.layout_cloud_detailed_title);
        cDetailedNickName = (TextView) findViewById(R.id.layout_cloud_detailed_detailed_nickname);
        cDetailedPhoneNumber = (TextView) findViewById(R.id.layout_cloud_detailed_phonenumber);
        cDetailedPaginalNumber = (TextView) findViewById(R.id.layout_cloud_detailed_paginalnumber);
        cDetailedAddress = (TextView) findViewById(R.id.layout_cloud_detailed_address);
        cDetailedType = (TextView) findViewById(R.id.layout_cloud_detailed_type);
        cDetailedTime = (TextView) findViewById(R.id.layout_cloud_detailed_servicetime_title);
        cDetailedColor = (TextView) findViewById(R.id.layout_cloud_detailed_color);
        cDetailedUnivalent = (TextView) findViewById(R.id.layout_cloud_detailed_univalent);
        cDetailedTotal = (TextView) findViewById(R.id.layout_cloud_detailed_total);
        back = (ImageView) findViewById(R.id.back);
        biaoti= (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("订单详情");
    }
}
