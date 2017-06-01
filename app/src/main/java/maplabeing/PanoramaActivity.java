package maplabeing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaView;

import ui.test.cn.xiaoyitong.R;

public class PanoramaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.yellow);
        setContentView(R.layout.activity_panorama);
        init();
        Intent intent = getIntent();
        String endX = intent.getStringExtra("endX");
        String endY = intent.getStringExtra("endY");
        StreetViewPanoramaView mPanoramaView = (StreetViewPanoramaView) findViewById(R.id.panorama_view);
        StreetViewPanorama mPanorama = mPanoramaView.getStreetViewPanorama();
        mPanorama.setPosition(Double.valueOf(endX), Double.valueOf(endY));

    }
   private void init(){
       ImageView back = (ImageView) findViewById(R.id.back);
       TextView biaoti = (TextView) findViewById(R.id.biaoti);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       biaoti.setText("全景地图");
    }
}
