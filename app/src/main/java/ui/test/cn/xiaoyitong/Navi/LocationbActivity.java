package ui.test.cn.xiaoyitong.Navi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.Navi.adapter.GridviewAdapter;
import ui.test.cn.xiaoyitong.Navi.entity.Address;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.backgroundlbs.DatabaseHelpter.MyDatabaseHelper;
import ui.test.cn.xiaoyitong.backgroundlbs.service.LocationService;
import ui.test.cn.xiaoyitong.utils.StatusBarUtil;
import ui.test.cn.xiaoyitong.utils.Utils;

public class LocationbActivity extends SwipeBackActivity {
    private ArrayList<Address> listaddress = new ArrayList<>();
    private int GET_MESSAGE = 1052;
    GridviewAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_MESSAGE) {
                adapter.notifyDataSetChanged();
            }
        }
    };
    private ImageView back;
    private TextView biaoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.location_main);
        //加载list
        loadlist();
        //创建数据库
        creatdb();
        //启动service
        // startService();
    }

    private void loadlist() {
        adapter = new GridviewAdapter(LocationbActivity.this, listaddress);
        GridView listview = (GridView) findViewById(R.id.list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LocationbActivity.this, DriveActivity.class);
                intent.putExtra("x", listaddress.get(position).getX());
                intent.putExtra("y", listaddress.get(position).getY());
                startActivity(intent);

            }
        });
        back = (ImageView) findViewById(R.id.back);
        biaoti = (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("停车导航");
    }

    private void creatdb() {
        MyDatabaseHelper my = new MyDatabaseHelper(this, "location.db", null, 1);
        my.getWritableDatabase();
        if (Utils.isNetworkAvailable(this)) {
            okhttp();

        } else {
            Toast.makeText(this, "没网络", Toast.LENGTH_SHORT).show();
        }
    }

    private void startService() {
        boolean aa = Utils.isServiceWork(this, "com.renbaojia.newcomers.background.LocationService");
        if (aa) {
            Toast.makeText(this, "服务正在运行", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "启动服务", Toast.LENGTH_SHORT).show();
            startService(new Intent(this, LocationService.class));
        }
    }

    private void okhttp() {
        String url = "http://123.206.92.38/SimpleSchool/locationservlet?opt=get_address";
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
                    JSONArray array = new JSONArray(string);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject mJSONObject = array.getJSONObject(i);
                        Address address = new Address();
                        address.setAddress(mJSONObject.getString("address"));
                        address.setX(mJSONObject.getString("X"));
                        address.setY(mJSONObject.getString("Y"));
                        getImage(mJSONObject.getString("img"), i);
                        listaddress.add(address);
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

    private void getImage(final String wwwimage, final int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(wwwimage);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
                    InputStream isp = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(isp);
                    listaddress.get(i).setImg(bitmap);
                    isp.close();
                    Message msg = new Message();
                    msg.what = GET_MESSAGE;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
