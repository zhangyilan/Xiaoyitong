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
 * Created by John on 2017/4/23.
 */

public class OrderInformationAdapter extends RecyclerView.Adapter<OrderInformationAdapter.ViewHolder> {
    // 数据集
    private List<OrderList> mOrderList;
   // private OnRecycleViewClickListener onRecycleViewClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_information, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        /*holder.expressListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressId=holder.id.getText().toString();
                onRecycleViewClickListener.onRecycleViewClick(v,expressId);
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderList expressList = mOrderList.get(position);
        Picasso.with(MyApplication.getContext()).load(R.drawable.bg).into(holder.releaseImage);
        holder.id.setText(expressList.getId());
        holder.orderTime.setText(expressList.getOrderTime());
        holder.orderStatus.setText(expressList.getStatus());
        holder.type.setText(expressList.getType());
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View expressListView;
        ImageView releaseImage;
        TextView id;
        TextView orderTime;
        TextView orderStatus;
        TextView type;
        public ViewHolder(View view) {
            super(view);
            expressListView = view;
            releaseImage = (ImageView) view.findViewById(R.id.recyclerview_order_image);
            type = (TextView) view.findViewById(R.id.recycelrview_order_type);
            orderTime = (TextView) view.findViewById(R.id.recyclerview_order_time);
            orderStatus = (TextView) view.findViewById(R.id.recyclerview_order_status);
            id = (TextView) view.findViewById(R.id.recyclerview_order_id);
        }
    }
/*
    public interface OnRecycleViewClickListener{
        void  onRecycleViewClick(View view, String expressId);
    }
*/
    public OrderInformationAdapter (List<OrderList> expressList) {
        mOrderList = expressList;
    }

/*
    public void  myRecycleViewClickListener(OnRecycleViewClickListener listener){
        onRecycleViewClickListener=listener;
    }
*/
}
