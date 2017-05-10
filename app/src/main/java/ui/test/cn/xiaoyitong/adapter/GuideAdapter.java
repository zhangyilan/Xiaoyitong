package ui.test.cn.xiaoyitong.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.GuideBean;

/**
 * Created by YanChunlin on 2017/4/20.
 */

public class GuideAdapter extends PagerAdapter {
    private ArrayList<GuideBean> mList;
    private Context context;
    private LayoutInflater mInflater;
    private View.OnClickListener mClickListener;

    public GuideAdapter(Context context, ArrayList<GuideBean> list) {
        this.context=context;
        this.mList=list;
        mInflater=LayoutInflater.from(context);
    }


    public void setClickListener(View.OnClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);//return view ==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //创建图片view容器
        View imageLayout = mInflater.inflate(R.layout.guide_adapter, container, false);
        //用户引导图片
        LinearLayout linearLayout = (LinearLayout) imageLayout.findViewById(R.id.image_view);
        //开始体验按钮
        Button startBtn = (Button) imageLayout.findViewById(R.id.hello_btn);
        //获取图片路径
        GuideBean itemBean  = mList.get(position);
        linearLayout.setBackgroundResource(itemBean.getIcon());
        //startBtn.setTag(path);
        //设置开始按钮的点击事件
        startBtn.setOnClickListener(mClickListener);
        if(position==4){
            startBtn.setVisibility(View.VISIBLE);
        } else {
            startBtn.setVisibility(View.INVISIBLE);
        }
        //将图片view加载到容器中
        ((ViewPager) container).addView(imageLayout, 0);
        return imageLayout;
    }
}

