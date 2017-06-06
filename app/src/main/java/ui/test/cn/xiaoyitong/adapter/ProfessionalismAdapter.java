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

import ui.test.cn.xiaoyitong.R;


/**
 * Created by John on 2017/5/25.
 */

public class ProfessionalismAdapter extends ArrayAdapter<Professionalism> {
    private int resourceId;

    public ProfessionalismAdapter(Context context, int resource, List<Professionalism> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Professionalism professionalism = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.item_professionalism_img);
            viewHolder.subject = (TextView) view.findViewById(R.id.item_professionalism_subject);
            viewHolder.department = (TextView) view.findViewById(R.id.item_professionalism_department);
            viewHolder.finish = (TextView) view.findViewById(R.id.item_professionalism_finish);
            viewHolder.score = (TextView) view.findViewById(R.id.item_professionalism_score);
            viewHolder.id = (TextView) view.findViewById(R.id.item_professionalism_id);
            viewHolder.status = (TextView) view.findViewById(R.id.item_professionalism_status);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Picasso.with(getContext()).load(R.drawable.icon).into(viewHolder.image);
        //viewHolder.image.setImageResource(R.mipmap.ic_launcher);
        viewHolder.subject.setText(professionalism.getSubject());
        viewHolder.department.setText(professionalism.getDepartment());
        viewHolder.finish.setText(professionalism.getFinish());
        viewHolder.score.setText(professionalism.getScore());
        viewHolder.id.setText(professionalism.getId());
        viewHolder.status.setText(professionalism.getStatus());
        return view;
    }
    class ViewHolder {
        ImageView image;
        TextView subject;
        TextView department;
        TextView finish;
        TextView score;
        TextView id;
        TextView status;
    }
}
