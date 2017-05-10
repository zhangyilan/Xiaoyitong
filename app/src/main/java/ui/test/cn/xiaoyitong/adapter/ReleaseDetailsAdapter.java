package ui.test.cn.xiaoyitong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;


/**
 * Created by John on 2017/4/15.
 */

public class ReleaseDetailsAdapter extends ArrayAdapter<ReleaseDetails> {
    private int resourceId;
    public ReleaseDetailsAdapter(Context context, int textViewResourceId, List<ReleaseDetails> objects) {
        super(context, textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DetailsViewHolder viewHolder;
        ReleaseDetails releaseDetails = getItem(position);
        View view;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new DetailsViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.layout_release_express_detailed_image);
            viewHolder.nickName = (TextView) view.findViewById(R.id.layout_release_express_detailed_nickname);
            viewHolder.name = (TextView) view.findViewById(R.id.layout_release_express_detailed_name);
            viewHolder.phoneNumber = (TextView) view.findViewById(R.id.layout_release_express_detailed_phonenumber_title);
            viewHolder.pickNumber = (TextView) view.findViewById(R.id.layout_release_express_detailed_picknumber);
            viewHolder.address = (TextView) view.findViewById(R.id.layout_release_express_detailed_address);
            viewHolder.urgent = (TextView) view.findViewById(R.id.layout_release_express_detailed_urgent);
            viewHolder.expectationtime = (TextView) view.findViewById(R.id.layout_release_express_detailed_expectationtime_title);
            viewHolder.type = (TextView) view.findViewById(R.id.layout_release_express_detailed_type);
            view.setTag(viewHolder);
        } else {
            view=convertView;
            viewHolder=(DetailsViewHolder) view.getTag();
        }
        Picasso.with(MyApplication.getContext()).load(R.mipmap.ic_launcher).into(viewHolder.image);
        viewHolder.nickName.setText(releaseDetails.getNickName());
        viewHolder.name.setText(releaseDetails.getName());
        viewHolder.phoneNumber.setText(releaseDetails.getPhoneNumber());
        viewHolder.pickNumber.setText(releaseDetails.getPickNumber());
        viewHolder.address.setText(releaseDetails.getAddress());
        viewHolder.urgent.setText(releaseDetails.getUrgent());
        viewHolder.expectationtime.setText(releaseDetails.getExpectationtime());
        viewHolder.type.setText(releaseDetails.getType());
        return view;
    }
}
class DetailsViewHolder{
    ImageView image;
    TextView nickName;
    TextView name;
    TextView phoneNumber;
    TextView pickNumber;
    TextView address;
    TextView urgent;
    TextView expectationtime;
    TextView type;
}
