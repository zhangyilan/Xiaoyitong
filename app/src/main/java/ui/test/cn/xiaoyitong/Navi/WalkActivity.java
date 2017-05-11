package ui.test.cn.xiaoyitong.Navi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.R;

public class WalkActivity extends BaseActivity {
    protected NaviLatLng mEndLatlng;
    NaviLatLng mStartLatlng;
    protected final List<NaviLatLng> sList = new ArrayList<>();
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    protected final List<NaviLatLng> eList = new ArrayList<>();
    int biaoshi = 0;
    String X;
    String Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.blacka);
        setContentView(R.layout.activity_way);
        Intent intent = getIntent();
        X = intent.getStringExtra("x");
        Y = intent.getStringExtra("y");
        mEndLatlng=new NaviLatLng(Double.valueOf(X),Double.valueOf(Y));
       // Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        //设置模拟导航速度
        mAMapNavi.setEmulatorNaviSpeed(20);
        initLocation();
    }

    public void onInitNaviSuccess() {
        mAMapNavi.calculateWalkRoute(mStartLatlng, mEndLatlng);
    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        if (mAMapNavi.startNavi(NaviType.GPS)) {
            //Toast.makeText(WalkActivity.this, "启动成功", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(WalkActivity.this, "启动失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                Double Latitude = loc.getLatitude();
                Double Longitude = loc.getLongitude();
                //Toast.makeText(WalkActivity.this, Latitude + "\n" + Longitude, Toast.LENGTH_SHORT).show();
                mStartLatlng = new NaviLatLng(Latitude, Longitude);
                sList.add(mStartLatlng);
                eList.add(mEndLatlng);
                if (biaoshi == 0) {
                    onInitNaviSuccess();
                    biaoshi = 1;
                }

            } else {
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        locationClient.onDestroy();
    }

}
