package ui.test.cn.xiaoyitong.MassOrganization.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ui.test.cn.xiaoyitong.MassOrganization.LocationEntity;
import ui.test.cn.xiaoyitong.R;


public class DetialsListAdapter  extends ArrayAdapter<LocationEntity> {
        private int resourceId;

         public DetialsListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LocationEntity> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LocationEntity locationEntity = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.locationImg = (ImageView) view.findViewById(R.id.locationImg);
            viewHolder.locationName = (TextView) view.findViewById(R.id.locationName);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
        assert locationEntity != null;
        viewHolder.locationName.setText(locationEntity.getLocationName());
        viewHolder.locationImg.setImageResource(locationEntity.getImgId());
        return view;
    }

    private class ViewHolder {
        ImageView locationImg;
        TextView locationName;
    }


}
