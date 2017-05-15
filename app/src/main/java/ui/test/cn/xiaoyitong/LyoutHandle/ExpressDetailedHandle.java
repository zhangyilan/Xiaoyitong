package ui.test.cn.xiaoyitong.LyoutHandle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;

import static ui.test.cn.xiaoyitong.InternetUtils.ExpressDetailedUtils.MosaicExpressInformation;

/**
 * Created by John on 2017/4/17.
 */

public class ExpressDetailedHandle extends SwipeBackActivity implements View.OnClickListener {
    private TextView customerNickname, layout_release_customer_type_title;
    private TextView customerType;
    private TextView customerSpecifications;
    private EditText customerPicknumber;
    private EditText customerPhonenumber;
    private EditText customerName;
    private EditText customerAddress;
    private TextView customerUrgent;
    private EditText customerPrice;
    private Button customerConfirm;
    private LinearLayout linerlayout1, linerlayout2, linerlayout3;
    private String nickName;
    private String type;
    private String specifications;
    private String pickNumber;
    private String phoneNumber;
    private String name;
    private String address;
    private String urgent;
    private String price;
    private String status;


    ArrayList<String> expressage = new ArrayList<>();
    ArrayList<String> expressage1 = new ArrayList<>();
    ArrayList<String> expressage2 = new ArrayList<>();
    OptionsPickerView pvNoLinkOptions, pvNoLinkOptions1, pvNoLinkOptions2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.layout_release_customer);

        init();

        customerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data();
                if ("".equals(nickName) || "".equals(type) || "".equals(specifications) || "".equals(pickNumber) || "".equals(phoneNumber)
                        || "".equals(name) || "".equals(address) || "".equals(urgent) || "".equals(price)) {
                    Toast.makeText(ExpressDetailedHandle.this, "所有信息都不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ExpressDetailedHandle.this,PayDemoActivity.class);
                    intent.putExtra("name","快递发布");
                    intent.putExtra("bewrite",nickName);
                    intent.putExtra("price",price);
                    startActivityForResult(intent,1);
                    String info = MosaicExpressInformation(nickName, type, specifications, pickNumber, phoneNumber, name, address, urgent, price);
                    sendRequestWithHttpClient(info);
                }
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    status = data.getStringExtra("status");
                }
                if ("1".equals(status)){
                    Toast.makeText(ExpressDetailedHandle.this,"发布成功",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExpressDetailedHandle.this,"发布失败",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void init() {
        linerlayout1 = (LinearLayout) findViewById(R.id.linerlayout1);
        linerlayout2 = (LinearLayout) findViewById(R.id.linerlayout2);
        linerlayout3 = (LinearLayout) findViewById(R.id.linerlayout3);
        SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
        String user_name=share.getString("user_name","没有登陆");
        customerNickname = (TextView) findViewById(R.id.layout_release_customer_nickname);
        customerNickname.setText(user_name);
        customerType = (TextView) findViewById(R.id.layout_release_customer_type);
        customerSpecifications = (TextView) findViewById(R.id.layout_release_customer_specifications);
        customerPicknumber = (EditText) findViewById(R.id.layout_release_customer_picknumber);
        customerPhonenumber = (EditText) findViewById(R.id.layout_release_customer_phonenumber);
        customerName = (EditText) findViewById(R.id.layout_release_customer_name);
        customerAddress = (EditText) findViewById(R.id.layout_release_customer_address);
        customerUrgent = (TextView) findViewById(R.id.layout_release_customer_urgent);
        customerPrice = (EditText) findViewById(R.id.layout_release_customer_price);
        customerConfirm = (Button) findViewById(R.id.layout_release_customer_confirmreleasebtn);
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        layout_release_customer_type_title = (TextView) findViewById(R.id.layout_release_customer_type_title);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("发布快递");
        initNoLinkOptionsPicker1();
        initNoLinkOptionsPicker2();
        initNoLinkOptionsPicker3();
        linerlayout1.setOnClickListener(this);
        linerlayout2.setOnClickListener(this);
        linerlayout3.setOnClickListener(this);

    }

    private void initNoLinkOptionsPicker1() {
        expressage.add("EMS快递");
        expressage.add("申通快递");
        expressage.add("圆通快递");
        expressage.add("中通快递");
        expressage.add("韵达快递");
        expressage.add("顺丰快递");
        expressage.add("天天快递");
        expressage.add("百世汇通");
        pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customerType.setText(expressage.get(options1));
            }
        }).setSubmitColor(Color.parseColor("#00B9DA"))
                .setCancelColor(Color.parseColor("#00B9DA")).setTitleText("快递公司").build();
        pvNoLinkOptions.setPicker(expressage);
    }

    private void initNoLinkOptionsPicker2() {
        expressage1.add("小件");
        expressage1.add("大件");
        pvNoLinkOptions1 = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customerSpecifications.setText(expressage1.get(options1));
            }
        }).setSubmitColor(Color.parseColor("#00B9DA"))
                .setCancelColor(Color.parseColor("#00B9DA"))
                .setTitleText("快递规格")
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvNoLinkOptions1.setPicker(expressage1);
    }

    private void initNoLinkOptionsPicker3() {
        expressage2.add("一般");
        expressage2.add("紧急");
        expressage2.add("特急");
        pvNoLinkOptions2 = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                customerUrgent.setText(expressage2.get(options1));
            }
        }).setSubmitColor(Color.parseColor("#00B9DA"))
                .setCancelColor(Color.parseColor("#00B9DA"))
                .setTitleText("紧急情况")
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvNoLinkOptions2.setPicker(expressage2);
    }

    private void data() {

        nickName = customerNickname.getText().toString();
        type = customerType.getText().toString();
        specifications = customerSpecifications.getText().toString();
        pickNumber = customerPicknumber.getText().toString();
        phoneNumber = customerPhonenumber.getText().toString();
        name = customerName.getText().toString();
        address = customerAddress.getText().toString();
        urgent = customerUrgent.getText().toString();
        price = customerPrice.getText().toString();
    }

    private void sendRequestWithHttpClient(String url) {
        final String method = "POST";
        String request = HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                System.out.println("成功" + response);
            }

            @Override
            public void onError(Exception e) {
                System.out.println("失败" + e);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linerlayout1:
                pvNoLinkOptions.show();
                break;
            case R.id.linerlayout2:
                pvNoLinkOptions1.show();
                break;
            case R.id.linerlayout3:
                pvNoLinkOptions2.show();
                break;
        }
    }

    ;
}
