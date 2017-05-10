package ui.test.cn.xiaoyitong.backgroundlbs.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;

/**
 * Created by renba on 2017/4/10.
 */

public class LocationService extends Service implements TencentLocationListener {
    private int error = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if (error == TencentLocation.ERROR_OK) {
            double latitude = tencentLocation.getLatitude();
            double longitude = tencentLocation.getLongitude();
            Toast.makeText(this, "纬度：" + latitude + "\n" + "经度：" + longitude, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
