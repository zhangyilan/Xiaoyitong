package ui.test.cn.xiaoyitong.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ui.test.cn.xiaoyitong.MainActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.GuideAdapter;
import ui.test.cn.xiaoyitong.entity.GuideBean;

/**
 * Created by YanChunlin on 2017/4/20.
 */

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private ArrayList<GuideBean> mList;
    private GuideAdapter mAdapter;
    private LinearLayout mTipsGroupView;
    private boolean isFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_main);
        initView();
        upDataView();
    }


    //初始化控件
    private void initView() {
        mViewPager=(ViewPager) findViewById(R.id.guide_main_vp);
        mTipsGroupView = (LinearLayout)findViewById(R.id.viewGroup);
        mList=new ArrayList<>();
        mList.add(new GuideBean(R.drawable.fy_first));
        mList.add(new GuideBean(R.drawable.fy_first1));
        mList.add(new GuideBean(R.drawable.fy_first2));
        mList.add(new GuideBean(R.drawable.fy_first3));
        mList.add(new GuideBean(R.drawable.fy_first));
        setupTipsImage();
        //存储app首次登录的信息，判断app是否首次部署
        SharedPreferences spre=getSharedPreferences("first",Activity.MODE_PRIVATE);
        isFirst=spre.getBoolean("status",true);
        if (!isFirst){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    //更新UI
    private void upDataView() {

        mAdapter=new GuideAdapter(this,mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        mAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTipImageSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //构建图片位置标记点
    private void setupTipsImage()
    {
        mTipsGroupView.removeAllViews();
        for(int i=0; i<mList.size(); i++)
        {
            ImageView imageView = new ImageView(this);
            if(i == 0){
                imageView.setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                imageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            imageView.setLayoutParams(new LinearLayout.LayoutParams(20,20));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(24, 24));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            mTipsGroupView.addView(imageView, layoutParams);
        }
    }

    //根据当前的图片位置来高亮相应的标记点
    private void setTipImageSelect(int selectItems){
        for(int i=0; i<mTipsGroupView.getChildCount(); i++)
        {
            View childView = mTipsGroupView.getChildAt(i);
            if(i == selectItems){
                childView.setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                childView.setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }
}


