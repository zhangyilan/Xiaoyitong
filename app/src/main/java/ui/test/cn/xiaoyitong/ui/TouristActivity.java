package ui.test.cn.xiaoyitong.ui;

import android.app.Activity;

/**
 * Created by YanChunlin on 2017/4/19.
 */

public class TouristActivity extends Activity {
//    private EditText phone;//手机号
//    private EditText auth_code;//验证码
//    private Button btn_code;//获取验证码
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        StatusBarUtil.StatusBarLightMode(this);
//        setContentView(R.layout.activity_tourist);
//        phone = (EditText) findViewById(R.id.phone);
//        auth_code = (EditText) findViewById(R.id.auth_code);
//        btn_code = (Button) findViewById(R.id.btn_code);
//        btn_code.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url="http://123.206.92.38:80/SimpleSchool/phonecodeServlet?opt=get_phone_code&phone="+phone.getText().toString()+"";
//                HttpUtil httpUtil=new HttpUtil();
//                if (httpUtil.isNetworkAvailable(TouristActivity.this)){
//                    httpUtil.getData(url, new HttpCallback() {
//                        @Override
//                        public void onFinish(String response) {
//                            Message message=new Message();
//                            message.what=1;
//                            message.obj=response;
//                            Log.d("验证码",response);
//                            handler.sendMessage(message);
//                        }
//
//                        @Override
//                        public void onerror(Exception e) {
//
//                        }
//                    });
//                }
//            }
//        });
//    }
//    Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what==1){
//                Log.d("验证码是:",msg.obj.toString());
//            }
//        }
//    };
}
