package ui.test.cn.xiaoyitong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Grade;

/**
 * Created by Administrator on 2017/4/18.
 */

public class

SoreAdapter extends ArrayAdapter<Grade> {
    private int resourceId;

    public SoreAdapter(Context context, int resourceId, List<Grade> grades) {
        super(context, resourceId, grades);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Grade grade = getItem(position);
        final ViewHolder viewHolder;
        final View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.re_score_name = (TextView) view.findViewById(R.id.re_score_name);//课程名称
            viewHolder.grade = (TextView) view.findViewById(R.id.grade);//成绩
            viewHolder.grade_point = (TextView) view.findViewById(R.id.grade_point);//成绩绩点
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.re_score_name.setText(grade.getCourse_name());
        viewHolder.grade.setText(grade.getCourse_grade());
        viewHolder.grade_point.setText(grade.getGrade_point());
        return view;
    }
    class ViewHolder {
        TextView re_score_name;
        TextView grade;
        TextView grade_point;
    }
}
