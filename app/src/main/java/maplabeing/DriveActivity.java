package maplabeing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import maplabeing.navi.BaseActivity;
import ui.test.cn.xiaoyitong.R;


public class DriveActivity extends BaseActivity {
    protected final List<NaviLatLng> sList = new ArrayList<>();
    protected final List<NaviLatLng> eList = new ArrayList<>();
    final int ACTIVITY_INTENT = 10824;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.blacka);
        setContentView(R.layout.activity_drive);
        Intent intent = getIntent();
         id = intent.getStringExtra("id");
        Log.d("sfdgfhggfd",id);
        String startX = intent.getStringExtra("startX");
        String startY = intent.getStringExtra("startY");
        String endX = intent.getStringExtra("endX");
        String endY = intent.getStringExtra("endY");
        sList.add(0, new NaviLatLng(Double.valueOf(startX), Double.valueOf(startY)));
        eList.add(0, new NaviLatLng(Double.valueOf(endX), Double.valueOf(endY)));
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(40);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ACTIVITY_INTENT) {
                Intent intent=new Intent(  DriveActivity.this,ParkActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=ACTIVITY_INTENT;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask,10000);
    }


}
