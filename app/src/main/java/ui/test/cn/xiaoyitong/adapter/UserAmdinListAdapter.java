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

public class UserAmdinListAdapter extends ArrayAdapter<Careerpublish> {
    private   int   resourseId;
    ListViewClickListener  listViewClickListener;

    public UserAmdinListAdapter(Context context, int resource, List<Careerpublish> objects) {
        super(context, resource, objects);
        this.resourseId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Careerpublish careerpublish=getItem(position);

        final View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourseId,null);
            viewHolder=new ViewHolder();
            viewHolder.listthem= (TextView) view.findViewById(R.id.publish_users_name);
            viewHolder.author= (TextView) view.findViewById(R.id.publish_users_content);
            viewHolder.pubulishImg= (ImageView) view.findViewById(R.id.publish_users_imag);


            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("id","当前"+careerpublish.getId());
                listViewClickListener.onRecycleViewClick(view,careerpublish.getId(),careerpublish.getTheme());
            }
        });
        viewHolder.listthem.setText(careerpublish.getTheme());
        viewHolder.author.setText(careerpublish.getPublish_branch());

        if(careerpublish.getImgBitmap()!=null){
           viewHolder.pubulishImg.setImageBitmap(careerpublish.getImgBitmap());
        }else {
            viewHolder.pubulishImg.setImageResource(R.drawable.icon);
        }
        return view;
    }
    class ViewHolder{
        ImageView pubulishImg;

        TextView listthem;
        TextView author;


    }
    public  interface    ListViewClickListener{
        void  onRecycleViewClick(View view, int id, String name);
    }
    //当执行这个方法的时候调用接口
    public void  myListViewClickListener(ListViewClickListener listener){
        listViewClickListener=listener;
    }

}
