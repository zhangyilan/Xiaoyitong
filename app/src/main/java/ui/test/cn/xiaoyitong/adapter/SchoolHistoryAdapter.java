package ui.test.cn.xiaoyitong.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import java.util.ArrayList;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.SchoolHistory;

/**
 * Created by yuan on 2017/1/16.
 */

public class SchoolHistoryAdapter extends PagerAdapter {
    private ArrayList<SchoolHistory>mList;
    private Context context;
    private LayoutInflater mInflater;
    private View.OnClickListener mClickListener;

    public SchoolHistoryAdapter(Context context, ArrayList<SchoolHistory> list) {
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
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
////创建图片view容器
////        View imageLayout = mInflater.inflate(R.layout.school_adapter, container, false);
//        //用户引导图片
////        LinearLayout linearLayout = (LinearLayout) imageLayout.findViewById(R.id.image_view);
//
//        //获取图片路径
//        SchoolHistory itemBean  = mList.get(position);
////        linearLayout.setBackgroundResource(itemBean.getIcon());
//        // startBtn.setTag(path);
//        //设置开始按钮的点击事件
//
//        //将图片view加载到容器中
////        ((ViewPager) container).addView(imageLayout, 0);
//        return imageLayout;
//    }
}
