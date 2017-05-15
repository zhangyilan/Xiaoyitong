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
 * Created by John on 2017/4/14.
 */

public class ExpressListAdapter extends RecyclerView.Adapter<ExpressListAdapter.ViewHolder> {
    private List<ExpressList> mExpressList;

    private OnRecycleViewClickListener onRecycleViewClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_release_express, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.expressListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressId=holder.id.getText().toString();
                onRecycleViewClickListener.onRecycleViewClick(v,expressId);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExpressList expressList = mExpressList.get(position);
        Picasso.with(MyApplication.getContext()).load(expressList.getImageId()).into(holder.releaseImage);
        holder.id.setText(expressList.getId());
        holder.expectationTime.setText(expressList.getExpectationTime());
        holder.releaseTime.setText(expressList.getReleaseTime());
        holder.type.setText(expressList.getType());
    }

    @Override
    public int getItemCount() {
        return mExpressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View expressListView;
        ImageView releaseImage;
        TextView id;
        TextView expectationTime;
        TextView releaseTime;
        TextView type;
        public ViewHolder(View view) {
            super(view);
            expressListView = view;
            releaseImage = (ImageView) view.findViewById(R.id.recyclerview_release_image);
            id = (TextView) view.findViewById(R.id.recyclerview_release_id);
            type = (TextView) view.findViewById(R.id.recycelrview_release_type);
            expectationTime = (TextView) view.findViewById(R.id.recyclerview_service_time);
            releaseTime = (TextView) view.findViewById(R.id.recyclerview_release_time);
        }
    }

    public interface OnRecycleViewClickListener{
        void  onRecycleViewClick(View view, String expressId);
    }

    public ExpressListAdapter (List<ExpressList> expressList) {
        mExpressList = expressList;
    }


    public void  myRecycleViewClickListener(OnRecycleViewClickListener listener){
        onRecycleViewClickListener=listener;
    }

}


