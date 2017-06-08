package ui.test.cn.xiaoyitong.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import maplabeing.MaplibeingActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.LyoutHandle.ProfessionalismHandle;
import ui.test.cn.xiaoyitong.Navi.LocationActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.GridviewAdapter;
import ui.test.cn.xiaoyitong.adviewpagermanger.ADBean;
import ui.test.cn.xiaoyitong.adviewpagermanger.TuTu;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallback;
import ui.test.cn.xiaoyitong.httpHelper.JsonHelper;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.AdminListActivity;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.BaodaoActivity;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.Comouter_two_Login_Activity;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.Courses_login;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.MenuGrandFind;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.SchoolHistoryMainActivity;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.newsMainActivity;
import ui.test.cn.xiaoyitong.utils.HttpUtil;

/**
 * Created by asus on 2017/4/2.
 */

public class FirstFragment extends Fragment {
    private PopupWindow mPopupWindow;
    private View view;
    private int screenwidth;
    private GridView gridview;
    private LinearLayout newsLinearLayout;
    private TextView biaoti;
    private ImageView back;
    private MaterialRefreshLayout materialRefreshLayout;

    /**
     * 轮播图对象列表
     */
    private List<ADBean> listADbeans;
    private ViewFlipper mFlipper;//新闻头条
    private String[] ad_imgurls = {
            "http://www.zhangyilan.me/img/adimg/img1.jpeg",
            "http://www.zhangyilan.me/img/adimg/img2.jpeg",
            "http://www.zhangyilan.me/img/adimg/img3.jpeg",
            "http://www.zhangyilan.me/img/adimg/img4.jpeg",
            "http://www.zhangyilan.me/img/adimg/img5.jpg"};
    //新闻轮播
    private String newsurl = "http://123.206.92.38:80/SimpleSchool/AppServlet?opt=gettitle";
    private TuTu tu;
    private Context mContext;
    private ViewPager ad_viewpager;
    private LinearLayout ll_dian;
    private TextView newstxt1, newstxt2, newstxt3, newstxt4, newstxt5;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab01, container, false);

//        view1 = getActivity().getLayoutInflater().inflate(R.layout.popwindow_item, null);
        mContext = getContext();
        intview();
        topbar();
        gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new GridviewAdapter(getContext()));
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics out = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(out);
        screenwidth = out.widthPixels;

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=4");
//                    Intent intent = new Intent(getActivity(), ExpressListHandle.class);
//                    startActivity(intent);
                    //成绩查询跳转
                    SharedPreferences share = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
                    String user_name = share.getString("user_name", "没有登陆");

                    if (user_name.equals("没有登陆")) {
                        Toast.makeText(getContext(), "您还未登陆,请登陆", Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    } else {
                        String url = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=get_formal&user=" + user_name + "";
                        HttpUtil httpUtil = new HttpUtil();
                        if (httpUtil.isNetworkAvailable(getActivity())) {
                            httpUtil.getData(url, new HttpCallback() {
                                @Override
                                public void onFinish(String respose) {
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = respose;
                                    handler.sendMessage(message);
                                }

                                @Override
                                public void onerror(Exception e) {
                                }
                            });
                        }
                    }

                }

                if (position == 1) {
                    SharedPreferences share = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
                    String user_name = share.getString("user_name", "没有登陆");

                    if (user_name.equals("没有登陆")) {
                        Toast.makeText(getActivity(), "您还未登陆,请登陆", Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    } else {
                        String url = "http://123.206.92.38:80/SimpleSchool/userservlet?opt=get_formal&user=" + user_name + "";
                        HttpUtil httpUtil = new HttpUtil();
                        if (httpUtil.isNetworkAvailable(getActivity())) {
                            httpUtil.getData(url, new HttpCallback() {
                                @Override
                                public void onFinish(String respose) {
                                    Message message = new Message();
                                    message.what = 2;
                                    message.obj = respose;
                                    handler.sendMessage(message);
                                }

                                @Override
                                public void onerror(Exception e) {
                                }
                            });
                        }
                    }
                }
                if (position == 2) {

                    HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=8");
                    Intent intent = new Intent(getActivity(), Comouter_two_Login_Activity.class);
                    startActivity(intent);

                }

                if (position == 3) {
                    HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=5");
                    Intent intent = new Intent(getActivity(), LocationActivity.class);
                    startActivity(intent);
                }
                if (position == 4) {
                    HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=6");
                    Intent intent = new Intent(getActivity(), MaplibeingActivity.class);
                    startActivity(intent);

                }
                //新生指南
                if (position == 5) {
                    Intent intent = new Intent(getActivity(), BaodaoActivity.class);
                    startActivity(intent);
                }
                //校历
                if (position == 6) {
                    HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=7");
                    Intent intent = new Intent(getActivity(), SchoolHistoryMainActivity.class);
                    startActivity(intent);
                }
               //素质
                if (position == 7) {
                    HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=8");
                    startHttpConnection();
                }

            }
        });
        initAD();
        initNews();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(newsurl)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Message message = new Message();
                message.what = 0;
                message.obj = resp;
                handler.sendMessage(message);
            }
        });
        return view;
    }

    /**
     * 初始化轮播图
     */
    private void initAD() {
        listADbeans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ADBean bean = new ADBean();
            bean.setId(i + "");
            bean.setImgUrl(ad_imgurls[i]);
            listADbeans.add(bean);
        }
        tu = new TuTu(ad_viewpager, ll_dian, mContext, listADbeans);
        tu.startViewPager(4000);//动态设置滑动间隔，并且开启轮播图
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void intview() {
        biaoti= (TextView) view.findViewById(R.id.tv_title);
        biaoti.setText("校易通");

        ad_viewpager = (ViewPager) view.findViewById(R.id.ad_viewpage);
        ll_dian = (LinearLayout) view.findViewById(R.id.ll_dian);
        newsLinearLayout = (LinearLayout) view.findViewById(R.id.news_linearlayout);

        mPopupWindow = new PopupWindow(view, android.widget.Toolbar.LayoutParams.WRAP_CONTENT, android.widget.Toolbar.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setWidth(400);
        mPopupWindow.setHeight(700);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setFocusable(true);
        //背景图
        Drawable statusQuestionDrawable = getActivity().getResources().getDrawable(R.drawable.popup_bg);
        mPopupWindow.setBackgroundDrawable(statusQuestionDrawable);
        newsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=1");
                Intent intent = new Intent(getActivity(), newsMainActivity.class);
                startActivity(intent);
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                WindowManager.LayoutParams lp = ((Activity) getContext()).getWindow()
                        .getAttributes();
                lp.alpha = 1.0f;
                ((Activity) getContext()).getWindow().setAttributes(lp);

            }
        });

        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
//        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.finishRefreshLoadMore();
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        Toast.makeText(getActivity(), "数据加载成功", Toast.LENGTH_SHORT).show();
                    }
                }, 4000);
            }
        });

    }


    /**
     * 初始化新闻头条
     */
    private void initNews() {
        newstxt1 = (TextView) view.findViewById(R.id.news_txt1);
        newstxt2 = (TextView) view.findViewById(R.id.news_txt2);
        newstxt3 = (TextView) view.findViewById(R.id.news_txt3);
        newstxt4 = (TextView) view.findViewById(R.id.news_txt4);
        newstxt5 = (TextView) view.findViewById(R.id.news_txt5);
        mFlipper = (ViewFlipper) view.findViewById(R.id.flipper);
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.push_up_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.push_up_out));
        mFlipper.startFlipping();
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String data = (String) msg.obj;
                    List<String> newstitle = JsonHelper.jsonjiesi(data);
                    if (newstitle.size() != 0) {
                        newstxt1.setText(newstitle.get(0).toString());
                        newstxt2.setText(newstitle.get(1).toString());
                        newstxt3.setText(newstitle.get(2).toString());
                        newstxt4.setText(newstitle.get(3).toString());
                        newstxt5.setText(newstitle.get(4).toString());
                    }
                    break;
                case 1:
                    if (msg.obj.equals("true")) {
                        HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=3");
                        startActivity(new Intent(getActivity(), MenuGrandFind.class));
                    } else {
                        new android.app.AlertDialog.Builder(getActivity()).setTitle("您不是正式用户！").setMessage("是否升级为正式用户！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getActivity(), "跳转中", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), UserUpgradehandle.class);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                    }
                    break;
                case 2:
                    if (msg.obj.equals("true")) {
                        HttpUtil.PostData("http://123.206.92.38:80/SimpleSchool/countClickServlet?opt=update_click&id=2");
                        startActivity(new Intent(getActivity(), Courses_login.class));
                    } else {
                        new android.app.AlertDialog.Builder(getActivity()).setTitle("您不是正式用户！").setMessage("是否升级为正式用户！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getActivity(), "跳转中", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), UserUpgradehandle.class);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                    }
                    break;
                default:
                    Toast.makeText(getContext(), "没有访问到数据", Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };

    private void topbar() {
        View PopupWindowview = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_item, null);
        LinearLayout linearLayout1 = (LinearLayout) PopupWindowview.findViewById(R.id.ll_item1);
        LinearLayout linearLayout2 = (LinearLayout) PopupWindowview.findViewById(R.id.ll_item2);
        LinearLayout linearLayout3 = (LinearLayout) PopupWindowview.findViewById(R.id.ll_item3);
        LinearLayout linearLayout4 = (LinearLayout) PopupWindowview.findViewById(R.id.ll_item4);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"发起聊天",Toast.LENGTH_SHORT).show();
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"添加朋友",Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"扫一扫",Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"收款付款",Toast.LENGTH_SHORT).show();
            }
        });
        final PopupWindow mPopupWindow = new PopupWindow(PopupWindowview, android.widget.Toolbar.LayoutParams.WRAP_CONTENT, android.widget.Toolbar.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setWidth(400);
        mPopupWindow.setHeight(600);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        //背景图
        Drawable statusQuestionDrawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusQuestionDrawable = getActivity().getDrawable(R.drawable.popup_bg);
        }
        mPopupWindow.setBackgroundDrawable(statusQuestionDrawable);
        Button btn_add = (Button) view.findViewById(R.id.button);
        // 进入添加好友页
        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });
        Button btn_popup = (Button) view.findViewById(R.id.button_pop);
        btn_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation scaleAnimation =
                        new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                // 设置动画执行的时间（单位：毫秒）
                scaleAnimation.setDuration(3000);
                //Interpolator类主要是用来控制android动画的执行速率
                scaleAnimation.setInterpolator(new BounceInterpolator());
                scaleAnimation.setFillEnabled(true);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.start();
                setBackgroundAlpha(0.5f);
                mPopupWindow.showAsDropDown(v);
            }

            /**
             * 设置添加屏幕的背景透明度
             * 屏幕透明度0.0-1.0 1表示完全不透明
             */
            public void setBackgroundAlpha(float bgAlpha) {

                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = bgAlpha;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {

            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();

            lp.alpha = 1f;
            getActivity().getWindow().setAttributes(lp);
        }

    }
    private void startHttpConnection (){
        SharedPreferences share = getActivity().getSharedPreferences("user",getActivity().MODE_PRIVATE);
        String user_name=share.getString("user_name","没有登陆");
        if (user_name.equals("没有登陆")){
            Toast.makeText(getActivity(),"您还未登陆,请登陆",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            String url = "http://123.206.92.38/SimpleSchool/userservlet?opt=is_student&user=" + user_name;
            String method = "GET";
            HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    if (response.equals("true")){
                        Intent intent = new Intent(getActivity(), ProfessionalismHandle.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), AdminListActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }


}
