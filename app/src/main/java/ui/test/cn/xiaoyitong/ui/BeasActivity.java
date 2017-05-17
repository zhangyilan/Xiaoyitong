package ui.test.cn.xiaoyitong.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import ui.test.cn.xiaoyitong.Navi.BaseActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.controller.HXSDKHelper;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.Courses_login;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.MenuGrandFind;
import ui.test.cn.xiaoyitong.ui.sonfragmeng.PersonalActiity;

/**
 * Created by asus on 2017/4/10.
 */

public class BeasActivity extends FragmentActivity implements View.OnClickListener {
    private LinearLayout mTabhome;
    private LinearLayout mTabluntuan;
    private LinearLayout mTabfri;
    private LinearLayout mTabshetuan;

    private ImageView mImghome;
    private ImageView mImgluntuan;
    private ImageView mImgfri;
    private ImageView mImgshetuan;

    private TextView txt_home, txt_luntuan, txt_fri, txt_shetuan;
    private TextView loginname;


    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;

    //    private int screenwidth;

    //    private ImageView menu_touxiang;
//    private RelativeLayout menu_list1, menu_list2, menu_list3, menu_list4, menu_list5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.transparent);

        setContentView(R.layout.activity_main1);

        initView();
        initEvent();
        setSelect(0);

    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // 把图片设置为亮的
        // 设置内容区域
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new FirstFragment();
                    transaction.add(R.id.id_content, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                mImghome.setImageResource(R.drawable.guide_home_press);
                txt_home.setTextColor(getResources().getColorStateList(R.color.txtfri));
                txt_luntuan.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                txt_fri.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                txt_shetuan.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new SecondFragment();
                    transaction.add(R.id.id_content, mTab02);
                } else {
                    transaction.show(mTab02);
                }
                mImgluntuan.setImageResource(R.drawable.guide_luntan_press);
                txt_luntuan.setTextColor(getResources().getColorStateList(R.color.txtfri));
                txt_home.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                txt_fri.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                txt_shetuan.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new ThirstFragment();
                    transaction.add(R.id.id_content, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                mImgfri.setImageResource(R.drawable.tab_friend_press);
                txt_fri.setTextColor(getResources().getColorStateList(R.color.txtfri));
                txt_home.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                txt_luntuan.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                txt_shetuan.setTextColor(getResources().getColorStateList(R.color.txtnomal));

                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new FourthFragment();
                    transaction.add(R.id.id_content, mTab04);
                } else {
                    transaction.show(mTab04);
                }
                mImgshetuan.setImageResource(R.drawable.guide_shetuan_press);
                txt_shetuan.setTextColor(getResources().getColorStateList(R.color.txtfri));
                txt_home.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                txt_luntuan.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                txt_fri.setTextColor(getResources().getColorStateList(R.color.txtnomal));
                break;
            default:
                break;
        }

        transaction.commit();
    }


    private void initEvent() {

        mTabhome.setOnClickListener(this);
        mTabluntuan.setOnClickListener(this);
        mTabfri.setOnClickListener(this);
        mTabshetuan.setOnClickListener(this);

//        menu_touxiang.setOnClickListener(this);
//        menu_list1.setOnClickListener(this);
//        menu_list2.setOnClickListener(this);
//        menu_list3.setOnClickListener(this);
//        menu_list4.setOnClickListener(this);
//        menu_list5.setOnClickListener(this);
    }

    private void initView() {
//        menu_touxiang = (ImageView) findViewById(R.id.touxiang_img);
//        menu_list1 = (RelativeLayout) findViewById(R.id.menu_list1);
//        menu_list2 = (RelativeLayout) findViewById(R.id.menu_list2);
//        menu_list3 = (RelativeLayout) findViewById(R.id.menu_list3);
//        menu_list4 = (RelativeLayout) findViewById(R.id.menu_list4);
//        menu_list5 = (RelativeLayout) findViewById(R.id.menu_list5);

      /*  loginname = (TextView) findViewById(R.id.loginname);
        try {
            if (!loginname.getText().toString().equals(EMChatManager.getInstance().getCurrentUser())) {
                loginname.setText(EMChatManager.getInstance().getCurrentUser());
            }
        } catch (Exception e) {

        }*/


        mTabhome = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mTabluntuan = (LinearLayout) findViewById(R.id.id_tab_friend);
        mTabfri = (LinearLayout) findViewById(R.id.id_tab_addr);
        mTabshetuan = (LinearLayout) findViewById(R.id.id_tab_setting);

        mImghome = (ImageView) findViewById(R.id.id_tab_wexin_img);
        mImgluntuan = (ImageView) findViewById(R.id.id_tab_friend_img);
        mImgfri = (ImageView) findViewById(R.id.id_tab_addr_img);
        mImgshetuan = (ImageView) findViewById(R.id.id_tab_setting_img);

        txt_home = (TextView) findViewById(R.id.txt_home);
        txt_luntuan = (TextView) findViewById(R.id.txt_luntuan);
        txt_fri = (TextView) findViewById(R.id.txt_fri);
        txt_shetuan = (TextView) findViewById(R.id.txt_shetuan);


    }

    @Override
    public void onClick(View view) {
        resetImgs();
        switch (view.getId()) {
            case R.id.id_tab_weixin:
                setSelect(0);
                break;
            case R.id.id_tab_friend:
                setSelect(1);
                break;
            case R.id.id_tab_addr:
                if (HXSDKHelper.getInstance().isLogined()) {
                    EMGroupManager.getInstance().loadAllGroups();
                    EMChatManager.getInstance().loadAllConversations();
                    setSelect(2);
                } else {
                    Toast.makeText(BeasActivity.this, "你还没有登陆！亲登陆后在操作！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BeasActivity.this, FirstActivity.class));
                }
                break;
            case R.id.id_tab_setting:
                setSelect(3);
                break;

            case R.id.touxiang_img:
                startActivity(new Intent(BeasActivity.this, PersonalActiity.class));
                break;
            case R.id.menu_list1:
                startActivity(new Intent(BeasActivity.this, MenuGrandFind.class));
                break;
            case R.id.menu_list2:
//                Toast.makeText(BeasActivity.this, "点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_list3:
                startActivity(new Intent(BeasActivity.this, Courses_login.class));
                break;
            case R.id.menu_list4:
//                Toast.makeText(BeasActivity.this, "点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_list5:
//                Toast.makeText(BeasActivity.this, "点击", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }
    }


    /**
     * 切换图片至暗色
     */
    private void resetImgs() {
        mImghome.setImageResource(R.drawable.guide_home_nm);
        mImgluntuan.setImageResource(R.drawable.guide_luntan_nm);
        mImgfri.setImageResource(R.drawable.tab_friend_nm);
        mImgshetuan.setImageResource(R.drawable.guide_shetuan_nm);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
