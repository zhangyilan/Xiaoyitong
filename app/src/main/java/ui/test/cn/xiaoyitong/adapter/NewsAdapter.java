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
import ui.test.cn.xiaoyitong.entity.news;

/**
 * Created by lenovo on 2017/04/13.
 */

public class NewsAdapter extends ArrayAdapter<news> {
    private   int   resourseId;
    ListViewClickListener  listViewClickListener;

    public NewsAdapter(Context context, int resource, List<news> objects) {
        super(context, resource, objects);
        this.resourseId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final news news=getItem(position);

        final View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourseId,null);
            viewHolder=new ViewHolder();
            viewHolder.listtitle= (TextView) view.findViewById(R.id.list_title);
            viewHolder.listTime= (TextView) view.findViewById(R.id.list_time);
            viewHolder.newsImg= (ImageView) view.findViewById(R.id.list_imag);
            viewHolder.author= (TextView) view.findViewById(R.id.list_author);
            viewHolder.read= (TextView) view.findViewById(R.id.list_read);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("id","当前"+news.getId());
                listViewClickListener.onRecycleViewClick(view,news.getId());
            }
        });
        viewHolder.listtitle.setText(news.getTitle());
        viewHolder.listTime.setText(news.getPostime());
        viewHolder.author.setText(news.getAuthor());
        Log.d("ce",String.valueOf(news.getRead()));
        viewHolder.read.setText(String.valueOf(news.getRead()));
        if(news.getImgBitmap()!=null){
           viewHolder.newsImg.setImageBitmap(news.getImgBitmap());
        }else {
            viewHolder.newsImg.setImageResource(R.drawable.news_item);
        }
        return view;
    }
    class ViewHolder{
        ImageView newsImg;

        TextView listtitle;
        TextView read;
        TextView author;
        TextView listTime;

    }
    public  interface    ListViewClickListener{
        void  onRecycleViewClick(View view, int id);
    }
    //当执行这个方法的时候调用接口
    public void  myListViewClickListener(ListViewClickListener listener){
        listViewClickListener=listener;
    }

}
