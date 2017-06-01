package maplabeing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.TMC;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import maplabeing.route.DriveSegmentListAdapter;
import maplabeing.util.AMapUtil;
import ui.test.cn.xiaoyitong.R;

public class DriveRouteDetailActivity extends AppCompatActivity {
    private DrivePath mDrivePath;
    private DriveRouteResult mDriveRouteResult;
    private TextView mTitleDriveRoute, mDesDriveRoute;
    private ListView mDriveSegmentList;
    private DriveSegmentListAdapter mDriveSegmentListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.yellow);
        setContentView(R.layout.activity_drive_route_detail);
        getIntentData();
        init();
    }

    private void init() {
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("驾车路线详情");
        mTitleDriveRoute = (TextView) findViewById(R.id.firstline);
        mDesDriveRoute = (TextView) findViewById(R.id.secondline);
        String dur = AMapUtil.getFriendlyTime((int) mDrivePath.getDuration());
        String dis = AMapUtil.getFriendlyLength((int) mDrivePath
                .getDistance());
        mTitleDriveRoute.setText(dur + "(" + dis + ")");
        int taxiCost = (int) mDriveRouteResult.getTaxiCost();
        mDesDriveRoute.setText("打车约" + taxiCost + "元");
        mDesDriveRoute.setVisibility(View.VISIBLE);
        configureListView();
    }

    private void configureListView() {
        mDriveSegmentList = (ListView) findViewById(R.id.bus_segment_list);
        mDriveSegmentListAdapter = new DriveSegmentListAdapter(
                this.getApplicationContext(), mDrivePath.getSteps());
        mDriveSegmentList.setAdapter(mDriveSegmentListAdapter);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mDrivePath = intent.getParcelableExtra("drive_path");
        mDriveRouteResult = intent.getParcelableExtra("drive_result");
        for (int i = 0; i < mDrivePath.getSteps().size(); i++) {
            DriveStep step = mDrivePath.getSteps().get(i);
            List<TMC> tmclist = step.getTMCs();
            for (int j = 0; j < tmclist.size(); j++) {
                String s = "" + tmclist.get(j).getPolyline().size();
                Log.i("MY", s + tmclist.get(j).getStatus()
                        + tmclist.get(j).getDistance()
                        + tmclist.get(j).getPolyline().toString());
            }
        }
    }

    public void onBackClick(View view) {
        this.finish();
    }
}

