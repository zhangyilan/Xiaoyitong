package ui.test.cn.xiaoyitong.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;

import ui.test.cn.xiaoyitong.Navi.BaseActivity;
import ui.test.cn.xiaoyitong.R;


public class DriveActivity extends BaseActivity {
    protected final NaviLatLng sList = new NaviLatLng();
    protected final NaviLatLng eList = new NaviLatLng();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_drive);
        Intent intent = getIntent();
        String startX = intent.getStringExtra("startX");
        String startY = intent.getStringExtra("startY");
        String endX = intent.getStringExtra("endX");
        String endY = intent.getStringExtra("endY");
        sList.setLatitude(Double.valueOf(startX));
        sList.setLongitude( Double.valueOf(startY));
        eList.setLatitude(Double.valueOf(endX));
        eList.setLongitude( Double.valueOf(endY));
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(20);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        mAMapNavi.calculateWalkRoute(sList, eList);
    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();

        if (mAMapNavi.startNavi(NaviType.EMULATOR)) {
            //Toast.makeText(DriveActivity.this, "启动成功", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(DriveActivity.this, "启动失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
    }

    @Override
    public void onArriveDestination() {
    }


    @Override
    public void onEndEmulatorNavi() {
    }


}
