package ui.test.cn.xiaoyitong.Navi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ui.test.cn.xiaoyitong.Navi.entity.Address;
import ui.test.cn.xiaoyitong.R;

/**
 * Created by renba on 2017/4/9.
 */

public class GridviewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Address> list = new ArrayList();

    public GridviewAdapter(Context context, ArrayList<Address> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 设置每个条目的界面
     */
    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        View view = View.inflate(context, R.layout.listview_item, null);
        ImageView img_icon = (ImageView) view.findViewById(R.id.locationImg);
        TextView txt_name = (TextView) view.findViewById(R.id.locationname);
        txt_name.setText(list.get(i).getAddress());
        img_icon.setImageBitmap(list.get(i).getImg());
        return view;
    }
}