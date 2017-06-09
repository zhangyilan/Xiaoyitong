package ui.test.cn.xiaoyitong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Careerpublish;


/**
 * Created by lenovo on 2017/04/13.
 */

public class PublishAdapter extends ArrayAdapter<Careerpublish> {
    private   int   resourseId;
    ListViewClickListener  listViewClickListener;
    mRecycleViewlongClickListener mRecycleViewlongClickListener;
    public PublishAdapter(Context context, int resource, List<Careerpublish> objects) {
        super(context, resource, objects);
        this.resourseId = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final Careerpublish careerpublish=getItem(position);

        final View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourseId,null);
            viewHolder=new ViewHolder();
            viewHolder.listthem= (TextView) view.findViewById(R.id.list_them);
            viewHolder.author= (TextView) view.findViewById(R.id.list_author);
            viewHolder.pubulishImg= (ImageView) view.findViewById(R.id.publish_imag);
            viewHolder.score= (TextView) view.findViewById(R.id.career_publish_quality_frade);
            viewHolder.publishtime= (TextView) view.findViewById(R.id.career_publish_time);
            viewHolder.status= (TextView) view.findViewById(R.id.career_publish_status);


            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("id","当前"+careerpublish.getId());
                listViewClickListener.onRecycleViewClick(view,careerpublish.getId(),careerpublish.getStatus(),careerpublish.getUsercount());
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mRecycleViewlongClickListener.onRecycleViewlongClick(view,position);
                return true;
            }
        });
        viewHolder.listthem.setText(careerpublish.getTheme());
        viewHolder.author.setText(careerpublish.getPublish_branch());
        viewHolder.publishtime.setText(careerpublish.getPublish_time());
        viewHolder.score.setText(String.valueOf(careerpublish.getQuality_frade()));
        viewHolder.status.setText(careerpublish.getStatus());
        if(careerpublish.getImgBitmap()!=null){
           viewHolder.pubulishImg.setImageBitmap(careerpublish.getImgBitmap());
        }else {
            viewHolder.pubulishImg.setImageResource(R.drawable.svtccicon);
        }
        return view;
    }
    class ViewHolder{
        ImageView pubulishImg;

        TextView listthem;
        TextView author;
        TextView publishtime;
        TextView score;
        TextView status;

    }
    public  interface    ListViewClickListener{
        void  onRecycleViewClick(View view, int id, String status, String usercount);
    }
    //当执行这个方法的时候调用接口
    public void  myListViewClickListener(ListViewClickListener listener){
        listViewClickListener=listener;
    }
    public  interface    mRecycleViewlongClickListener{//定义接口重写为接口添加了一个isbn参数
        void  onRecycleViewlongClick(View view, int position);
    }
    public void  myRecycleViewlongClickListener(mRecycleViewlongClickListener listener){//当执行这个方法的时候调用接口
        mRecycleViewlongClickListener=listener;
    }

}
