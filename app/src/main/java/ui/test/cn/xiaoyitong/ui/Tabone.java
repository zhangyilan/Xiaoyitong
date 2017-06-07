package ui.test.cn.xiaoyitong.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.navi.model.NaviLatLng;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import ui.test.cn.xiaoyitong.R;

/**
 * Created by YanChunlin on 2017/5/30.
 */

public class Tabone extends Activity {
    private TextView content;
    private TextView txt_name;
    private RollPagerView mRollViewPager;
    private ImageView btn_back;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private NaviLatLng mStartLatlng = new NaviLatLng();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxxz);
        String name = getIntent().getStringExtra("name1");
        txt_name = (TextView) findViewById(R.id.name);
        txt_name.setText(name);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        content = (TextView) findViewById(R.id.content);
        content.setText(Html.fromHtml("一、入学报到时间:详见《录取通知书》<p>\n" +
                "二、报到时凭《录取通知书》和身份证等有效证件报到。<p>\n" +
                "三、新生如不能暗示报道，须向我院提出书面申请，经同意后方可延期报到；否则，视为自行放弃入学资格。<p>\n" +
                "四、中共党员须由当地县、市一级组织部门开具的党组织关系介绍信（党员介绍信函头请写：中共四川省直属机关工委），团员凭团员证接转组织关系，相关接转手续报到时交由相关系。<p>\n" +
                "五、凡被我院录取的新生，城镇户口原则上不迁户。农村户口是否迁户，由学生自愿确定。迁户必须在入学时办理，过时不再办理迁户手续。报到时迁户证明交相关系。<p>\n" +
                "六、按各省（市）招办规定，凡需自带纸质档案入校的，请持《录取通知书》到当地招办（或中学）提取档案，报到时交由相关系。<p>\n" +
                "七、请新生复印好《录取通知书》，以备办理其它手续。<p>"));

        mRollViewPager = (RollPagerView) findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());

        mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
        inita();
    }

    private void inita() {
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
        mOption.setInterval(1000);//可选，设置定位间隔。默认为2秒
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
                Double Latitude = loc.getLatitude();
                Double Longitude = loc.getLongitude();
                mStartLatlng.setLatitude(Latitude);
                mStartLatlng.setLongitude(Longitude);
            }

        }

    };

    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.ra,
                R.drawable.rb,
                R.drawable.rc,
                R.drawable.rd,
                R.drawable.re,
                R.drawable.rg,
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }

    //导航
    public void svtcc(View view) {
        Intent intent = new Intent(this, DriveActivity.class);
        if(mStartLatlng!=null){
        intent.putExtra("startX", mStartLatlng.getLatitude()+"");
        intent.putExtra("startY", mStartLatlng.getLongitude()+"");
        intent.putExtra("endX", "30.686414");
        intent.putExtra("endY", "103.818818");
        startActivity(intent);

    }}
}
