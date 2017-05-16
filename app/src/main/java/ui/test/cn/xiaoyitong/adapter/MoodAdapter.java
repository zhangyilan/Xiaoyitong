package ui.test.cn.xiaoyitong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ui.test.cn.xiaoyitong.emo.EmojiconTextView;
import ui.test.cn.xiaoyitong.entity.Mood;
import ui.test.cn.xiaoyitong.ui.MoodActivity;
import ui.test.cn.xiaoyitong.R;


/**
 * Created by YanChunlin on 2017/4/25.
 */

public class MoodAdapter extends ArrayAdapter<Mood> {
    private int resourceId;
    public MoodAdapter(Context context, int textViewResourceId, List<Mood> objects) {
        super(context, textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Mood mood = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.username = (TextView) view.findViewById(R.id.user_name);
            viewHolder.moodtime = (TextView) view.findViewById(R.id.mood_time);
            viewHolder.content_image = (ImageView) view.findViewById(R.id.content_image);
            viewHolder.mood_info = (TextView) view.findViewById(R.id.mood_info);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.username.setText(mood.getName());
        viewHolder.moodtime.setText(mood.getTime());
        viewHolder.content_image.setImageResource(mood.getImg());
        viewHolder.mood_info.setText(mood.getContent());
        LinearLayout mf_comment = (LinearLayout) view.findViewById(R.id.mf_comment);
        mf_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MoodActivity.class);
                intent.putExtra("username", mood.getName());
                intent.putExtra("time", mood.getTime());
                intent.putExtra("img", mood.getImg());
                intent.putExtra("content", mood.getContent());
                getContext().startActivity(intent);
            }
        });
        return view;
    }
    class ViewHolder {
        TextView username;
        TextView moodtime;
        ImageView content_image;
        TextView mood_info;
    }
}
