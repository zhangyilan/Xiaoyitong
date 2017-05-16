package ui.test.cn.xiaoyitong.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;

import java.io.File;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.controller.HXSDKHelper;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.PersonalActiity;
import ui.test.cn.xiaoyitong.utils.DataCleanManager;
import ui.test.cn.xiaoyitong.utils.Myutils;
import ui.test.cn.xiaoyitong.utils.VersionUpdateUtils;

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
    View view;
    private TextView username;
    private TextView usertitle;

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
        username = (TextView) view.findViewById(R.id.username);
        mTitle = (TextView) view.findViewById(R.id.main_textview_title);
        if(!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())){
            username.setText(EMChatManager.getInstance().getCurrentUser());
        }
        if(!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())){
            mTitle.setText(EMChatManager.getInstance().getCurrentUser());
        }
        mToolbar = (Toolbar) view.findViewById(R.id.main_toolbar);

        mTitleContainer = (LinearLayout) view.findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.main_appbar);
        chache_size = (TextView) view.findViewById(R.id.chache_size);
        LinearLayout list1 = (LinearLayout) view.findViewById(R.id.lin_list1);
        LinearLayout list2 = (LinearLayout) view.findViewById(R.id.lin_list2);
        LinearLayout list3 = (LinearLayout) view.findViewById(R.id.lin_list3);
        LinearLayout list4 = (LinearLayout) view.findViewById(R.id.lin_list4);
        LinearLayout list5 = (LinearLayout) view.findViewById(R.id.lin_list5);
        LinearLayout list6 = (LinearLayout) view.findViewById(R.id.lin_list6);
        LinearLayout list7 = (LinearLayout) view.findViewById(R.id.lin_list7);
        @SuppressLint("SdCardPath") File file = new File("/data/data/ui.test.cn.xiaoyitong/cache");
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
        list7.setOnClickListener(this);


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
                String mversion = Myutils.getVersion(getActivity());
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
            case R.id.lin_list7:
                if (HXSDKHelper.getInstance().isLogined()) {
                    EMGroupManager.getInstance().loadAllGroups();
                    EMChatManager.getInstance().loadAllConversations();
                    logout();
                } else {
                    Toast.makeText(getActivity(), "你还没有登陆！亲登陆后在操作！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
        }
    }

    void logout() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        MyApplication.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // 重新显示登陆页面
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
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
