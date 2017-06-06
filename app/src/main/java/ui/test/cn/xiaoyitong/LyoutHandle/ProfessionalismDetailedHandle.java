package ui.test.cn.xiaoyitong.LyoutHandle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.ProfessionalismDetailed;
import ui.test.cn.xiaoyitong.backgroundlbs.DatabaseHelpter.MyDatabaseHelper;


/**
 * Created by John on 2017/5/26.
 */

public class ProfessionalismDetailedHandle extends Activity{
    private String id;
    private ImageView img;
    private TextView title;
    private TextView status;
    private TextView value;
    private TextView time;
    private TextView address;
    private TextView professionalismClass;
    private TextView department;
    private TextView content;
    private TextView activityBG;

    private List<ProfessionalismDetailed> list = new ArrayList<ProfessionalismDetailed>();
    private String signTime;
    private String signOutTime;
    private String signStatus = null;
    private String signStatus2 = null;
    private View activityView;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    final ProfessionalismDetailed professionalismDetailed = (ProfessionalismDetailed) msg.obj;
                    Picasso.with(ProfessionalismDetailedHandle.this).load(professionalismDetailed.getImgUrl()).into(img);
                    title.setText(professionalismDetailed.getTitle());
                    value.setText(professionalismDetailed.getValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    time.setText(professionalismDetailed.getStartTime() + "——" +professionalismDetailed.getEndTime());
                    try {
                        Date startTime = ConverToDate(professionalismDetailed.getStartTime());
                        Date endTime  = ConverToDate(professionalismDetailed.getEndTime());
                        Date currentTime = new Date(System.currentTimeMillis());
                        if ((startTime.getTime() - currentTime.getTime()) > 0){
                            status.setText("未开始");
                        } else if (((startTime.getTime() - currentTime.getTime()) <= 0) && ((endTime.getTime() - currentTime.getTime()) > 0)){
                            status.setText("进行中");
                        } else if ((endTime.getTime() - currentTime.getTime()) <= 0) {
                            status.setText("已结束");
                        } else {
                            status.setText("未知");
                        }
                        long judge = startTime.getTime() - endTime.getTime();
                        System.out.println(startTime+"\n"+endTime);
                        System.out.println(judge);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    address.setText(professionalismDetailed.getAddress());
                    professionalismClass.setText(professionalismDetailed.getProfessionalismClass());
                    department.setText(professionalismDetailed.getDepartment());
                    content.setText(professionalismDetailed.getContent());
                    activityBG.setText(professionalismDetailed.getActivityBG());
                    break;
                case 1:
                    System.out.println("1" + msg.obj.toString());
                    if (msg.obj.toString().equals("true")){
                        signStatus2 = "true";
                        operation();
                    } else {
                        Snackbar.make(activityView, "管理员未开启签到！", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    break;
                case 2:
                    System.out.println("2:" + msg.obj.toString());
                    if (msg.obj.toString().equals("true")){
                        signStatus = "true";
                        toastWinow(activityView);
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_professionalism_detailed);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.d("ProfessionalismDetailed",id);
        initProfessionalismDetailed();
        startHttpAction();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityView = view;
                signStatus = "false";
                signStatus2 = "false";
                if (status.getText().toString().equals("未开始") || status.getText().toString().equals("已结束")){
                    Snackbar.make(activityView, "请确认活动信息哦！", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    detection();



                    /*switch (signStatus) {
                        case 0:
                            Snackbar.make(view, "签到失败！", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        case 1:
                            Snackbar.make(view, "签到成功！", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        case 2:
                            Snackbar.make(view, "签退成功！", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        case 3:
                            Snackbar.make(view, "签退失败！", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        default:
                            break;
                    }*/

                }
            }
        });
    }

    private void startHttpAction() {
        String url = "http://123.206.92.38:80/SimpleSchool/userJoinServlet?opt=get_school_activity&id=" + id;
        String method = "GET";
        HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try{
                    String pimg = null;
                    String ptitle = null;
                    String pstatus = null;
                    String pvalue = null;
                    String pstartTime = null;
                    String pendTime = null;
                    String paddress = null;
                    String pprofessionalismClass = null;
                    String pdepartment = null;
                    String pcontent = null;
                    String pactivityBG = null;
                    JSONArray jsonArray=new JSONArray(response.toString());
                    JSONObject jsonobject = jsonArray.getJSONObject(0);
                    System.out.println("開始解析數據");
                    pimg = jsonobject.getString("activity_img");
                    ptitle = jsonobject.getString("theme");
                    //pstatus = jsonobject.getString("");
                    pvalue = jsonobject.getString("quality_frade");
                    pstartTime = jsonobject.getString("start_time");
                    pendTime = jsonobject.getString("end_time");
                    paddress = jsonobject.getString("activity_address");
                    pprofessionalismClass = jsonobject.getString("activity_type");
                    pdepartment = jsonobject.getString("publish_branch");
                    pcontent = jsonobject.getString("activity_suggest");
                    pactivityBG = jsonobject.getString("activity_background");
                    Message message = new Message();
                    ProfessionalismDetailed professionalismDetailed = new ProfessionalismDetailed(pimg,ptitle,pstatus,pvalue,pstartTime,pendTime,paddress,pprofessionalismClass,pdepartment,pcontent,pactivityBG);
                    System.out.println(professionalismDetailed);
                    message.obj = professionalismDetailed;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (Exception e){

                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void initProfessionalismDetailed() {
        img = (ImageView) findViewById(R.id.professionalism_detailed_img);
        title = (TextView) findViewById(R.id.professionalism_detailed_title);
        status = (TextView) findViewById(R.id.professionalism_detailed_status);
        value = (TextView) findViewById(R.id.professionalism_detailed_value);
        time = (TextView) findViewById(R.id.professionalism_detailed_time);
        address = (TextView) findViewById(R.id.professionalism_detailed_address);
        professionalismClass = (TextView) findViewById(R.id.professionalism_detailed_class);
        department = (TextView) findViewById(R.id.professionalism_detailed_department);
        content = (TextView) findViewById(R.id.professionalism_detailed_content);
        activityBG = (TextView) findViewById(R.id.professionalism_detailed_activitybg);
    }

    public static Date ConverToDate(String strDate) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.parse(strDate);
    }

    public static String ConverToString(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(date);
    }

    private void operation(){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            Date currentTime = new Date(System.currentTimeMillis());
            String strCurrentTime = format.format(currentTime);
            TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            String szImei = TelephonyMgr.getDeviceId();//获取手机识别码
            SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
            String user_name=share.getString("user_name","没有登陆");
            String url = "http://123.206.92.38:80/SimpleSchool/userJoinServlet?opt=user_insert_activity&student_id=" + user_name
                    + "&school_activity_id="+ id
                    + "&sign_in_time=" + strCurrentTime
                    + "&sign_out_time=" + strCurrentTime
                    + "&unique_code=" + szImei;
            String method = "GET";
            HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Message message = new Message();
                    message.obj = response;
                    message.what = 2;
                    handler.sendMessage(message);
                }

                @Override
                public void onError(Exception e) {
                }
            });
    }

    private void detection(){
        String url = "http://123.206.92.38:80/SimpleSchool/userJoinServlet?opt=get_sign_in";
        String method = "GET";
        HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message = new Message();
                message.obj = response;
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void toastWinow(View v){
        System.out.println("判断:" + signStatus2 + "   签到:" +signStatus);
        if (signStatus2.equals("true") && signStatus.equals("true")){
            Snackbar.make(v, "签到成功！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(v, "签到失败！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }



}
