package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.GuideAdapter;
import ui.test.cn.xiaoyitong.adapter.ShchoolhistoryAdapter;
import ui.test.cn.xiaoyitong.entity.GuideBean;

/**
 * Created by yuan on 2017/1/16.
 */

public class SchoolHistory_Activity extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private ArrayList<GuideBean> mList;
    private ShchoolhistoryAdapter mAdapter;
    private LinearLayout mTipsGroupView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guidea_main);
        initView();
        Intent intent=getIntent();
        String month=intent.getStringExtra("month");
        if (month.equals("二月")){
            upDataView(0);
        }
        if (month.equals("三月")){
            upDataView(1);
        }
        if (month.equals("四月")){
            upDataView(2);
        }
        if (month.equals("五月")){
            upDataView(3);
        }
        if (month.equals("六月")){
            upDataView(4);
        }

        if (month.equals("七月")){
            upDataView(5);
        }
        if (month.equals("八月")){
            upDataView(6);
        }
        if (month.equals("九月")){
            upDataView(7);
        }
        if (month.equals("十月")){
            upDataView(8);
        }
        if (month.equals("十一月")){
            upDataView(9);
        }
        if (month.equals("十二月")){
            upDataView(10);
        }
        if (month.equals("一月")){
            upDataView(11);
        }

    }


    //初始化控件
    private void initView() {
        mViewPager=(ViewPager) findViewById(R.id.guide_main_vp);
        mTipsGroupView = (LinearLayout)findViewById(R.id.viewGroup);
        mList=new ArrayList<>();

        mList.add(new GuideBean(R.drawable.feb));
        mList.add(new GuideBean(R.drawable.mar));
        mList.add(new GuideBean(R.drawable.apr));
        mList.add(new GuideBean(R.drawable.mm));
        mList.add(new GuideBean(R.drawable.jun));
        mList.add(new GuideBean(R.drawable.july));
        mList.add(new GuideBean(R.drawable.aug));
        mList.add(new GuideBean(R.drawable.sep));
        mList.add(new GuideBean(R.drawable.oct));
        mList.add(new GuideBean(R.drawable.nov));
        mList.add(new GuideBean(R.drawable.dec));
        mList.add(new GuideBean(R.drawable.jan));

        setupTipsImage();
    }
    //更新UI
    private void upDataView(int i) {

        mAdapter=new ShchoolhistoryAdapter(this,mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(i);

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
            imageView.setLayoutParams(new LinearLayout.LayoutParams(5,5));

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
