package maplabeing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import maplabeing.entity.Parks;
import maplabeing.overlay.DrivingRouteOverlay;
import maplabeing.util.AMapUtil;
import maplabeing.util.StatusBarLightMode;
import maplabeing.util.ToastUtil;
import maplabeing.util.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.R;

public class MaplibeingActivity extends AppCompatActivity implements View.OnClickListener, AMap.OnMapClickListener,
        AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener {
    private AMap aMap;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    //    private LatLonPoint mStartPoint = new LatLonPoint(39.942295,116.335891);//起点，39.942295,116.335891
//    private LatLonPoint mEndPoint = new LatLonPoint(39.995576,116.481288);//终点，39.995576,116.481288
    private int GONE_VISIBLE = 0;
    private final int ROUTE_TYPE_DRIVE = 2;
    private LinearLayout rote;
    //private RelativeLayout mBottomLayout, mHeadLayout;
    // private TextView mRotueTimeDes, mRouteDetailDes;
    private ProgressDialog progDialog = null;// 搜索时进度条
    private ProgressDialog locatiionprogDialog = null;// 搜索时进度条
    private TextView distance, number, park_name;
    private MapView mMapView;
    private final int GET_MESSAGE = 1052;
    private LinearLayout infolinearLayout, details;
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private MyLocationStyle myLocationStyle;
    private UiSettings mUiSettings;
    private int Traffic = 0;
    private LinearLayout weixing, dingwei, lukuang, nearby, linearLayout111, search;
    private int SATELLITE = 0;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private NaviLatLng mStartLatlng = new NaviLatLng();
    private NaviLatLng mEndLatlng = new NaviLatLng();
    int BIAOSHI = 0;
    private DrivingRouteOverlay drivingRouteOverlay;
    private MarkerOptions markerOptions;
    private int id;
    private int BIANLIANG=0;
    private int BIANLIANG1=0;
    List<Parks> parkslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        StatusBarLightMode.FlymeSetStatusBarLightMode(getWindow(), true);
        setContentView(R.layout.maplibeingactivity_main);
        init();
        mMapView.onCreate(savedInstanceState);
        //地图的设置
        MapSetting();
        if (Utils.isNetworkAvailable(this)) {
            initLocation();
            //初始化控件
            showProgressDialog1();


            okhttp();
            addMarkersToMap1();
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(30.689482, 103.817131), 18, 0, 0)));
        } else {
            Toast.makeText(this, "当前无法连接网络，请打开网络！", Toast.LENGTH_LONG).show();
        }
    }

    private void setfromandtoMarker() {
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(new LatLonPoint(mStartLatlng.getLatitude(), mStartLatlng.getLongitude())))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        markerOptions = new MarkerOptions()
                .position(AMapUtil.convertToLatLng(new LatLonPoint(parkslist.get(id - 1).getParklatitude(), parkslist.get(id - 1).getParklongitude())))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end));
        mEndLatlng.setLatitude(parkslist.get(id - 1).getParklatitude());
        mEndLatlng.setLatitude(parkslist.get(id - 1).getParklongitude());
        aMap.addMarker(markerOptions);

    }

    private void MapSetting() {
        if (aMap == null) {

            aMap = mMapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomBy(8));
            mUiSettings = aMap.getUiSettings();
        }

        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.parseColor("#f6a806"));
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        mUiSettings.setScaleControlsEnabled(false);
        myLocationStyle.radiusFillColor(FILL_COLOR);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(false);
    }

    private void init() {
        mContext = this.getApplicationContext();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        number = (TextView) findViewById(R.id.number);
        park_name = (TextView) findViewById(R.id.park_name);
        lukuang = (LinearLayout) findViewById(R.id.lukuang);
        weixing = (LinearLayout) findViewById(R.id.weixing);
        mMapView = (MapView) findViewById(R.id.map);
        distance = (TextView) findViewById(R.id.distance);
        nearby = (LinearLayout) findViewById(R.id.nearby);
        dingwei = (LinearLayout) findViewById(R.id.dingwei);
        details = (LinearLayout) findViewById(R.id.details);
        LinearLayout zoom_in = (LinearLayout) findViewById(R.id.zoom_in);
        LinearLayout zoom_out = (LinearLayout) findViewById(R.id.zoom_out);
        LinearLayout guard = (LinearLayout) findViewById(R.id.guard);
        rote = (LinearLayout) findViewById(R.id.rote);
        LinearLayout panorama = (LinearLayout) findViewById(R.id.panorama);
        infolinearLayout = (LinearLayout) findViewById(R.id.infolinearLayout);
        linearLayout111 = (LinearLayout) findViewById(R.id.linearLayout111);
        search = (LinearLayout) findViewById(R.id.search);
        infolinearLayout.setVisibility(View.GONE);

        nearby.setOnClickListener(this);
        details.setOnClickListener(this);
        panorama.setOnClickListener(this);
        guard.setOnClickListener(this);
        lukuang.setOnClickListener(this);
        weixing.setOnClickListener(this);
        dingwei.setOnClickListener(this);
        zoom_in.setOnClickListener(this);
        zoom_out.setOnClickListener(this);
    }

    private void addMarkersToMap1() {
        for (int i = 0; i < parkslist.size(); i++) {
            MarkerOptions
                    markerOptions = new MarkerOptions()
                    .title(String.valueOf(parkslist.get(i).getId()))
                    .position(new LatLng(parkslist.get(i).getParklatitude(), parkslist.get(i).getParklongitude()))
                    .icon(BitmapDescriptorFactory.fromBitmap(getMyBitmap(String.valueOf(parkslist.get(i).getParkusenumber()))));
            aMap.addMarker(markerOptions);

        }
        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerClickListener(this);

    }

    protected Bitmap getMyBitmap(String pm_val) {
        Bitmap bitmap = BitmapDescriptorFactory.fromResource(
                R.drawable.biaozhu).getBitmap();
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight());
        Canvas canvas = new Canvas(bitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40f);
        textPaint.setColor(Color.BLUE);
        canvas.drawText(pm_val, 50, 50, textPaint);
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        locationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
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
                if (BIAOSHI == 0) {
                    Double Latitude = loc.getLatitude();
                    Double Longitude = loc.getLongitude();
                    mStartLatlng.setLatitude(Latitude);
                    mStartLatlng.setLongitude(Longitude);
                    BIAOSHI = 1;
                    locatiionprogDialog.dismiss();
                }

            }
        }
    };

    @Override
    public View getInfoContents(Marker arg0) {

        return null;
    }

    @Override
    public View getInfoWindow(Marker arg0) {

        return null;
    }

    @Override
    public void onInfoWindowClick(Marker arg0) {


    }


    @Override
    public void onMapClick(LatLng arg0) {
        if (GONE_VISIBLE == 0) {
            linearLayout111.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            infolinearLayout.setVisibility(View.GONE);
            GONE_VISIBLE = 1;
        } else {
            if (BIANLIANG == 1) {
                infolinearLayout.setVisibility(View.VISIBLE);
            }
            linearLayout111.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            GONE_VISIBLE = 0;
        }
    }
    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartLatlng == null) {
            ToastUtil.show(mContext, "定位中，稍后再试...");
            return;
        }
        if (mEndLatlng == null) {
            ToastUtil.show(mContext, "终点未设置");
        }
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                new LatLonPoint(mStartLatlng.getLatitude(), mStartLatlng.getLongitude()), new LatLonPoint(parkslist.get(id - 1).getParklatitude(), parkslist.get(id - 1).getParklongitude()));
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        dissmissProgressDialog();
        aMap.clear();
        MapSetting();
        addMarkersToMap1();
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    if (drivingRouteOverlay != null) {
                        drivingRouteOverlay.removeFromMap();
                    }
                    drivingRouteOverlay = new DrivingRouteOverlay(
                            mContext, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    // mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    distance.setText(des);
                    //mRouteDetailDes.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    // mRouteDetailDes.setText("打车约"+taxiCost+"元");
                    rote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            intent.putExtra("drive_result",
                                    mDriveRouteResult);
                            startActivity(intent);
                        }
                    });

                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(mContext, "对不起，没有搜索到相关数据！");
                }

            } else {
                ToastUtil.show(mContext, "对不起，没有搜索到相关数据！");
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }


    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {

    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在加载...");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    private void showProgressDialog1() {
        if (locatiionprogDialog == null)
            locatiionprogDialog = new ProgressDialog(this);
        locatiionprogDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        locatiionprogDialog.setIndeterminate(false);
        locatiionprogDialog.setCancelable(false);
        locatiionprogDialog.setMessage("正在定位...");
        locatiionprogDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lukuang:
                if (Traffic == 0) {
                    aMap.setTrafficEnabled(true);//显示交通
                    Traffic = 1;
                } else {
                    aMap.setTrafficEnabled(false);//显示交通
                    Traffic = 0;
                }
                break;
            case R.id.weixing:
                if (SATELLITE == 0) {
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式
                    SATELLITE = 1;
                } else {
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 设置卫星地图模式
                    SATELLITE = 0;
                }
                break;
            case R.id.details:
                Intent intent3 = new Intent(MaplibeingActivity.this, ParkDetialsActivity.class);
                intent3.putExtra("name", parkslist.get(id - 1).getParkname() + "");
                intent3.putExtra("totalnumber", parkslist.get(id - 1).getParktotalnumber() + "");
                intent3.putExtra("address", parkslist.get(id - 1).getParkaddress() + "");
                intent3.putExtra("image", parkslist.get(id - 1).getParkimage() + "");
                startActivity(intent3);
                break;
            case R.id.guard:
                Intent intent = new Intent(MaplibeingActivity.this, DriveActivity.class);
                intent.putExtra("id", id + "");
                intent.putExtra("startX", mStartLatlng.getLatitude() + "");
                intent.putExtra("startY", mStartLatlng.getLongitude() + "");
                intent.putExtra("endX", parkslist.get(id - 1).getParklatitude() + "");
                intent.putExtra("endY", parkslist.get(id - 1).getParklongitude() + "");
                startActivity(intent);
                break;
            case R.id.panorama:
                Intent intent1 = new Intent(MaplibeingActivity.this, PanoramaActivity.class);
                intent1.putExtra("endX", parkslist.get(id - 1).getParklatitude() + "");
                intent1.putExtra("endY", parkslist.get(id - 1).getParklongitude() + "");
                startActivity(intent1);
                break;

            case R.id.nearby:
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(30.689482, 103.817131), 18, 0, 0)));
                if (Utils.isNetworkAvailable(this)) {
                    okhttp();

                } else {
                    Toast.makeText(this, "当前无法连接网络，请打开网络！", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.dingwei:
                locationClient.startLocation();
                MapSetting();
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(mStartLatlng.getLatitude(), mStartLatlng.getLongitude()), 18, 0, 0)));

                break;
            case R.id.zoom_in:
                changeCamera(CameraUpdateFactory.zoomIn(), null);
                break;
            case R.id.zoom_out:
                changeCamera(CameraUpdateFactory.zoomOut(), null);
                break;

        }
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        aMap.animateCamera(update, 500, callback);

    }

    private void okhttp() {
        String url = "http://123.206.92.38/park/parks";
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
                        Parks park = new Parks();
                        park.setId(mJSONObject.getInt("id"));
                        park.setParkname(mJSONObject.getString("parkname"));
                        park.setParklatitude(mJSONObject.getDouble("parklatitude"));
                        park.setParklongitude(mJSONObject.getDouble("parklongitude"));
                        park.setParktotalnumber(mJSONObject.getString("parktotalnumber"));
                        park.setParkusenumber(mJSONObject.getString("parkusenumber"));
                        park.setParkimage(mJSONObject.getString("parkimage"));
                        park.setParkaddress(mJSONObject.getString("parkaddress"));
                        parkslist.add(park);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_MESSAGE) {
                addMarkersToMap1();
                //地图标注
            }
        }
    };

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (Utils.isNetworkAvailable(this)) {
            if (aMap != null) {
                String title = marker.getTitle();
                id = Integer.parseInt(title);
                infomarker(marker);
                jumpPoint(marker);
            }
            infolinearLayout.setVisibility(View.VISIBLE);
            BIANLIANG=1;
        } else {
            Toast.makeText(this, "当前无法连接网络，请打开网络！", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void infomarker(Marker marker) {
        for (int i = 0; i < parkslist.size(); i++) {
            String title = marker.getTitle();
            if (title.equals(String.valueOf(parkslist.get(i).getId()))) {
                park_name.setText(parkslist.get(i).getParkname());
                number.setText(parkslist.get(i).getParkusenumber());

            }
        }

        setfromandtoMarker();
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);

    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;


        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void onRideRouteSearched(RideRouteResult arg0, int arg1) {

    }

}