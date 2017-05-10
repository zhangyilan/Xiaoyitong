package ui.test.cn.xiaoyitong.LyoutHandle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Map;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.service.AuthResult;
import ui.test.cn.xiaoyitong.service.PayUntils;


/**
 * 重要说明:
 * <p>
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class PayDemoActivity extends FragmentActivity {
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	//private String orderInfo;
	private String name;
	private String bewrite;
	private String price;
	private TextView name_tv;
	private TextView bewrite_tv;
	private TextView price_tv;
	private Button back;
	private String status = "0";

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					@SuppressWarnings("unchecked")
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						status = "1";
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					} else {
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
						Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
					}
					break;
				}
				case SDK_AUTH_FLAG: {
					@SuppressWarnings("unchecked")
					AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
					String resultStatus = authResult.getResultStatus();

					// 判断resultStatus 为“9000”且result_code
					// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
					if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
						// 获取alipay_open_id，调支付时作为参数extern_token 的value
						// 传入，则支付账户为该授权账户
						Toast.makeText(PayDemoActivity.this,
								"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
								.show();
					} else {
						// 其他状态值则为授权失败
						Toast.makeText(PayDemoActivity.this,
								"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

					}
					break;
				}
				default:
					break;
			}
		}

		;
	};
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_pay);
		init();
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra("status",status);
				setResult(RESULT_OK,intent);
				finish();
			}
		});
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	private void init() {
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		bewrite = intent.getStringExtra("bewrite");
		price = intent.getStringExtra("price");

		name_tv = (TextView) findViewById(R.id.layout_pay_name_tv);
		bewrite_tv = (TextView) findViewById(R.id.layout_pay_bewrite_name_tv);
		price_tv = (TextView) findViewById(R.id.layout_pay_price);
		back = (Button) findViewById(R.id.layout_pay_back);

		name_tv.setText(name);
		bewrite_tv.setText(bewrite);
		price_tv.setText(price);
	}

	/**
	 * 支付宝支付业务
	 *
	 * @param v
	 */
	public void payV2(View v) {
		final String orderInfo = PayUntils.PayV2(name,bewrite,price);
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		Thread payThread = new Thread(payRunnable);
		payThread.start();
		System.out.println("orderInfo:" + orderInfo);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("status",status);
		setResult(RESULT_OK,intent);
		finish();
	}

	/*private void sendRequestWithHttpClient(){
		final String method = "GET";
		String address = "http://123.206.92.38:80/SimpleSchool/AlipyServlet?opt=get_alipy&name=" + name +"&bewrite=" + bewrite + "&price=" + price;
		HttpUtil.sendHttpRequest(address, method, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				System.out.println(response);
				orderInfo = response;
				System.out.println(orderInfo);
			}

			@Override
			public void onError(Exception e) {
			}
		});
	}*/



	/**
	 * get the sdk version. 获取SDK版本号
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}


	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("PayDemo Page")
				.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
				.build();
		return new Action.Builder(Action.TYPE_VIEW)
				.setObject(object)
				.setActionStatus(Action.STATUS_TYPE_COMPLETED)
				.build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}
}

