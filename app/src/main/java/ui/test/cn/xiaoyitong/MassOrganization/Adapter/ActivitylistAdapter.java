package ui.test.cn.xiaoyitong.MassOrganization.Adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ui.test.cn.xiaoyitong.MassOrganization.LocationEntity;
import ui.test.cn.xiaoyitong.R;


public class ActivitylistAdapter extends ArrayAdapter<LocationEntity> {
    private int resourceId;
    private Activity context;

    public ActivitylistAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<LocationEntity> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LocationEntity locationEntity = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.linerlayout);
            viewHolder.locationImg = (ImageView) view.findViewById(R.id.locationImg);
            viewHolder.locationName = (EditText) view.findViewById(R.id.activiy_neirong);
            viewHolder. title=(TextView) view.findViewById(R.id.locationname);
            viewHolder.img = (ImageView) view.findViewById(R.id.img);

            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                }
            });
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        assert locationEntity != null;
        viewHolder.img.setImageBitmap(locationEntity.getActivity_neirong_img());
        viewHolder.title.setText(locationEntity.getActivitytitleName());
        viewHolder.locationName.setText(locationEntity.getActivity_name());
        viewHolder.locationImg.setImageBitmap(locationEntity.getActivity_img());
        return view;
    }
    private class ViewHolder {
        LinearLayout linearLayout;
        ImageView locationImg;
        TextView title;
        ImageView img;
        EditText locationName;
    }


}
