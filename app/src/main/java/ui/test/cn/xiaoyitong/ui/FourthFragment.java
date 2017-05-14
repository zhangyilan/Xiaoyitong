package ui.test.cn.xiaoyitong.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.PersonalActiity;
import ui.test.cn.xiaoyitong.utils.DataCleanManager;
import ui.test.cn.xiaoyitong.utils.Myutils;
import ui.test.cn.xiaoyitong.utils.VersionUpdateUtils;

/**
 * Created by asus on 2017/4/2.
 */

public class FourthFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private LinearLayout mTitleContainer;
    private TextView mTitle, chache_size;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private LinearLayout list1, list2, list3, list4, list5, list6;
    View view;
    //本地版本号
    private String mversion;
    //------****** 缓存相关****----------
    private final int CLEAN_SUC = 1001;
    private final int CLEAN_FAIL = 1002;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab04, container, false);
        bindActivity();
        mAppBarLayout.addOnOffsetChangedListener(this);
        mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        return view;
    }

    private void bindActivity() {
        mToolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        mTitle = (TextView) view.findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) view.findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.main_appbar);
        chache_size = (TextView) view.findViewById(R.id.chache_size);
        list1 = (LinearLayout) view.findViewById(R.id.lin_list1);
        list2 = (LinearLayout) view.findViewById(R.id.lin_list2);
        list3 = (LinearLayout) view.findViewById(R.id.lin_list3);
        list4 = (LinearLayout) view.findViewById(R.id.lin_list4);
        list5 = (LinearLayout) view.findViewById(R.id.lin_list5);
        list6 = (LinearLayout) view.findViewById(R.id.lin_list6);
        File file = new File("/data/data/ui.test.cn.xiaoyitong/cache");
        try {
            chache_size.setText(DataCleanManager.getCacheSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        list1.setOnClickListener(this);
        list2.setOnClickListener(this);
        list3.setOnClickListener(this);
        list4.setOnClickListener(this);
        list5.setOnClickListener(this);
        list6.setOnClickListener(this);


    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_list1:
                startActivity(new Intent(getActivity(), PersonalActiity.class));
                break;
            case R.id.lin_list2:

                break;
            case R.id.lin_list3:
                Toast.makeText(getActivity(), "正在检查更新", Toast.LENGTH_SHORT).show();
                //获取当前应用程序的版本号
                mversion = Myutils.getVersion(getActivity());
                //实例化VersionUpdateUtils，传入参数
                final VersionUpdateUtils updateUtils = new VersionUpdateUtils(mversion,
                        getActivity());
                //判断网络是否可用
                if (Myutils.isNetworkAvailable(getActivity())) {
                    new Thread() {
                        @Override
                        public void run() {
                            // 开启子线程获取服务器版本号
                            updateUtils.getCloudVersion();
                        }
                    }.start();
                } else {
                    //没网就直接进入主界面
                    Toast.makeText(getActivity(), "无网络，请打开网络", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lin_list4:
                showUpdateDialog();
                break;
            case R.id.lin_list5:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.lin_list6:

                break;

        }
    }

    /**
     * 弹出更新提示对话框
     */
    private void showUpdateDialog() {
        //创建dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("清理缓存");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("确认清理缓存？");
        builder.setCancelable(false);// 设置不能点击手机返回按钮隐藏对话框
        builder.setIcon(R.mipmap.ic_launcher);
        // 设置立即升级按钮点击事件
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataCleanManager.cleanInternalCache(getActivity());
                chache_size.setText("0KB");

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.show();


    }
}
