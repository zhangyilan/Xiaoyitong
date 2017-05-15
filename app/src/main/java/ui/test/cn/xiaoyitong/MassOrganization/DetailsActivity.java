package ui.test.cn.xiaoyitong.MassOrganization;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.MassOrganization.Adapter.ActivitylistAdapter;
import ui.test.cn.xiaoyitong.MassOrganization.entity.Organization;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.customControls.MainScrollView;
import ui.test.cn.xiaoyitong.utils.Utils;

public class DetailsActivity extends SwipeBackActivity implements View.OnClickListener {
    private ListView listview;
    List<LocationEntity> aa = new ArrayList<>();
    private MainScrollView swipe_ly;
    ActivitylistAdapter aaaaa;
    Button imgmore;
    Bitmap bitmap;
    String aaaa = "";
    String activity_include, activity_name, shetuan_img;
    ImageView tmg;
    TextView textview,biaoti;
    TextView textView2;
    private PopupWindow mPopupWindow;
    Organization organizationList = new Organization();
    private int GET_MESSAGE = 1052;
    public static final int SHOW_IMAGE = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_MESSAGE) {
                textview.setText(aaaa);
            }
            if (msg.what == SHOW_IMAGE) {
                textview.setText(aaaa);
                biaoti.setText(organizationList.getName());
                tmg.setImageBitmap(organizationList.getImg());
                textView2.setText(organizationList.getName());
                aaaaa.notifyDataSetChanged();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.activity_details);
        //获取控件
        initcontrols();
        init();
        //接收参数
        getintent();
        initpopupView();
        //初始化list
        loadlistview();
        //MainScrollView滑动刷新
        myMainScrollView();
        init();
    }

    private void initcontrols() {
        textview = (TextView) findViewById(R.id.textview);
        textView2 = (TextView) findViewById(R.id.textView2);
        swipe_ly = (MainScrollView) findViewById(R.id.swipe_ly);
        listview = (ListView) findViewById(R.id.listview);
        tmg = (ImageView) findViewById(R.id.tmg);
        tmg.setOnClickListener(this);
    }

    private void myMainScrollView() {
        swipe_ly.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (swipe_ly.isbottomAnimation()) {
                    //  Toast.makeText(DetailsActivity.this, "滑动", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void loadlistview() {

        aaaaa = new ActivitylistAdapter(DetailsActivity.this, R.layout.listview_activity, aa);
        listview.setAdapter(aaaaa);
        Utils.setListViewHeight(listview);
        listview.setFocusable(false);
    }


    public void getintent() {
        Intent intent = getIntent();
        String a = intent.getStringExtra("position");
        Toast.makeText(this, "接收" + a, Toast.LENGTH_SHORT).show();
        okhttp(a);
        okhttp1(a);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initpopupView() {
        View popupView = getLayoutInflater().inflate(R.layout.popupwindow, null);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        TextView textView = (TextView) popupView.findViewById(R.id.textfabu);
        textView.setOnClickListener(this);
        Drawable statusQuestionDrawable = getDrawable(R.mipmap.conversation_options_bg);
        mPopupWindow.setBackgroundDrawable(statusQuestionDrawable);
        imgmore = (Button) findViewById(R.id.imgmore);
        // imgmore.setVisibility(View.GONE);
        imgmore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgmore:
                mPopupWindow.showAsDropDown(v);
                break;
            case R.id.textfabu:
                break;
        }

    }

    private void okhttp(String a) {
        String url = "http://123.206.92.38:80/SimpleSchool/corporationservlet?opt=minutecorporation&corporation_id=" + (Integer.parseInt(a) + 1);
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
                analysisJsona(response.body().string());

            }

            private void analysisJsona(String a) {
                analysisJson(a);
                analysisJson1(a);
            }

            private void analysisJson(String string) {
                try {
                    JSONObject mJSONObject = null;
                    JSONArray array = new JSONArray(string);
                    for (int i = 0; i < array.length(); i++) {
                        mJSONObject = array.getJSONObject(i);
                        activity_name = mJSONObject.getString("activity_name");
                        activity_include = mJSONObject.getString("activity_include");
                        String activity_img = mJSONObject.getString("activity_img");
                        getImage(activity_img, i);
                        getImage1(shetuan_img, i);

                        aa.add(i, new LocationEntity( null, activity_include, activity_name));

                    }
                    Message m = new Message();
                    m.what = GET_MESSAGE;
                    handler.sendMessage(m);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private void analysisJson1(String string) {
                try {
                    JSONArray array = new JSONArray(string);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject mJSONObject = array.getJSONObject(i);
                        if (i == array.length() - 1) {
                            aaaa = mJSONObject.getString("corporation_summary");
                        }
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

    private void okhttp1(final String a) {
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
                        if (i == Integer.valueOf(a)) {
                            JSONObject mJSONObject = array.getJSONObject(i);
                            organizationList.setName(mJSONObject.getString("corporation_name"));
                            shetuan_img= mJSONObject.getString("corporation_img");
                            getImage(shetuan_img);

                        }

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

    private void getImage(final String wwwimage) {
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
                    bitmap = BitmapFactory.decodeStream(isp);
                    organizationList.setImg(bitmap);
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
    private void getImage1(final String wwwimage, final int a) {
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
                    Bitmap bitmap1 = BitmapFactory.decodeStream(isp);
                    aa.get(a).setActivity_img(bitmap1);
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
    private void getImage(final String wwwimage, final int a) {
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
                    aa.get(a).setActivity_neirong_img(bitmap);
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
    private void init() {
        ImageView back = (ImageView) findViewById(R.id.back);
        biaoti = (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText(organizationList.getName());
    }
}
