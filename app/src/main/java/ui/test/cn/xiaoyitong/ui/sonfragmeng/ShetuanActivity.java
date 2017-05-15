package ui.test.cn.xiaoyitong.ui.sonfragmeng;

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

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.MassOrganization.Adapter.OrganizationAdapter;
import ui.test.cn.xiaoyitong.MassOrganization.DetailsActivity;
import ui.test.cn.xiaoyitong.MassOrganization.entity.Organization;
import ui.test.cn.xiaoyitong.R;

/**
 * Created by asus on 2017/4/2.
 */

public class ShetuanActivity extends SwipeBackActivity {
    private GridView gridView;
    List<Organization> organizationList = new ArrayList<>();
    OrganizationAdapter organizationAdapter;
    public static final int SHOW_IMAGE = 1;
    View view;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_IMAGE) {

                organizationAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//          getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.shetuan);
        getGridview();
        okhttp();

    }

    public void getGridview() {
        gridView = (GridView) findViewById(R.id.grid);
        organizationAdapter = new OrganizationAdapter(ShetuanActivity.this, organizationList);
        gridView.setAdapter(organizationAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShetuanActivity.this, DetailsActivity.class);
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
            }
        });
    }

    private void okhttp() {
        String url = "http://123.206.92.38/SimpleSchool/corporationservlet?opt=getcorporation";
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
                        Organization ogan = new Organization();
                        ogan.setName(mJSONObject.getString("corporation_name"));
                        getImage(mJSONObject.getString("corporation_img"), i);
                        organizationList.add(ogan);
                    }
                    Message m = new Message();
                    m.what = SHOW_IMAGE;
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
                    organizationList.get(i).setImg(bitmap);
                    isp.close();
                    Message msg = new Message();
                    msg.what = SHOW_IMAGE;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
