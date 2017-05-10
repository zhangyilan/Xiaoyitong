package ui.test.cn.xiaoyitong.MassOrganization.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.MassOrganization.entity.Organization;
import ui.test.cn.xiaoyitong.R;

/**
 * GridView显示界面用到的Adapter
 * Created by asus on 2017/3/10.
 */

public class OrganizationAdapter extends BaseAdapter {
    private Context context;
    List<Organization> organizationList = new ArrayList<>();

    public OrganizationAdapter(Context context, List<Organization> organizationList) {
        this.context = context;
        this.organizationList = organizationList;
    }


    @Override
    public int getCount() {
        return organizationList.size();
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
        View view = View.inflate(context, R.layout.item_organization, null);
        ImageView img_icon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView txt_name = (TextView) view.findViewById(R.id.tv_name);
        img_icon.setImageBitmap(organizationList.get(i).getImg());
        txt_name.setText(organizationList.get(i).getName());
        return view;
    }


}
