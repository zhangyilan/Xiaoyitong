package ui.test.cn.xiaoyitong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;

/**
 * Created by John on 2017/4/18.
 */

public class CloudListAdapter extends RecyclerView.Adapter<CloudListAdapter.ViewHolder>{
    private List<CloudList> mCloudList;

    OnRecycleViewClickListener onRecycleViewClickListener;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cloud, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.expressListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CloudList cloudList = mCloudList.get(position);
                String id = holder.expressId.getText().toString();//用id查询点击的item详细信息
                onRecycleViewClickListener.onRecycleViewClick(v,id);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CloudList cloudList = mCloudList.get(position);

        Picasso.with(MyApplication.getContext()).load(cloudList.getImageUrl()).into(holder.image);
        holder.title.setText(cloudList.getTitle());
        holder.time.setText(cloudList.getServiceTime());
        holder.price.setText(cloudList.getPrice());
        holder.expressId.setText(cloudList.getId());
    }

    @Override
    public int getItemCount() {
        return mCloudList.size();
    }

    public interface OnRecycleViewClickListener{
        void  onRecycleViewClick(View view, String expressId);
    }

    public CloudListAdapter (List<CloudList> cloudList) {
        mCloudList = cloudList;
    }


    public void  myRecycleViewClickListener(OnRecycleViewClickListener listener){
        onRecycleViewClickListener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View expressListView;
        ImageView image;
        TextView title;
        TextView time;
        TextView price;
        TextView expressId;
        public ViewHolder(View view) {
            super(view);
            expressListView = view;
            image = (ImageView) view.findViewById(R.id.recyclerview_cloud_image);
            title = (TextView) view.findViewById(R.id.recycelrview_cloud_title);
            time = (TextView) view.findViewById(R.id.recyclerview_cloud_service_time);
            price = (TextView) view.findViewById(R.id.recyclerview_cloud_price);
            expressId = (TextView) view.findViewById(R.id.recyclerview_cloud_id);
        }
    }
}
