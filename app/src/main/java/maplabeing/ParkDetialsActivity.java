package maplabeing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ui.test.cn.xiaoyitong.R;

public class ParkDetialsActivity extends AppCompatActivity {
    String name, totalnumber, address, image;
    private int GET_MESSAGE = 1052;
    private  TextView addresstext,chewei;
    private  ImageView imageView;
    Bitmap bitmap;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_MESSAGE) {
                imageView.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.yellow);
        setContentView(R.layout.activity_park_detials);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        totalnumber = intent.getStringExtra("totalnumber");
        address = intent.getStringExtra("address");
        image = intent.getStringExtra("image");
        init();
        getImage();
    }

    private void init() {

        chewei = (TextView) findViewById(R.id.chewei);
         addresstext = (TextView) findViewById(R.id.address);
        imageView = (ImageView) findViewById(R.id.imageView);
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText(name);
        addresstext.setText(address);
        chewei.setText(totalnumber);
    }

    private void getImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(image);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
                    InputStream isp = httpURLConnection.getInputStream();
                     bitmap = BitmapFactory.decodeStream(isp);
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
