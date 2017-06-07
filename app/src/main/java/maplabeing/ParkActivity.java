package maplabeing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import maplabeing.canvas.CanvasView;
import maplabeing.entity.Parkentity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.R;

public class ParkActivity extends AppCompatActivity {
    private final int GET_MESSAGE = 1052;
    private  int width;
    private int height;
    private String index;
    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_MESSAGE) {
                init();
            }
        }
    };
    List<Parkentity> listPark = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.yellow);
        setContentView(R.layout.activity_park);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        index = intent.getStringExtra("index");
        okhttp(id);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        init1();
    }

    private void init() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.root);
        final CanvasView view = new CanvasView(this, width, height, listPark,index);
        view.setMinimumHeight(height);
        view.setMinimumWidth(width);
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);

    }

    private void init1() {
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("停车场信息");
    }

    private void okhttp(String id) {
        String url = "https://renbaojia.com/parkInfo?name=" + id;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                analysisJson(response.body().string());
            }

            private void analysisJson(String string) {
                try {
                    Log.d("aaaaaa", string);
                    JSONArray array = new JSONArray(string);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject mJSONObject = array.getJSONObject(i);
                        Parkentity park = new Parkentity();
                        park.setPlateNumber(mJSONObject.getString("plateNumber"));
                        park.setIsNull(mJSONObject.getInt("isNull"));
                        park.setParkTime(mJSONObject.getString("parkTime"));
                        park.setYuyue(mJSONObject.getString("yuyue"));
                        listPark.add(park);
                    }
                    Message m = new Message();
                    m.what = GET_MESSAGE;
                    handler.sendMessage(m);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
