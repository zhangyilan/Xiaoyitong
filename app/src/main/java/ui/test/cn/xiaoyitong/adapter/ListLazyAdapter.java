package ui.test.cn.xiaoyitong.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ui.test.cn.xiaoyitong.R;

/**
 * <p>列表的LazyAdapter类</p>
 * 
 * @author Ramboo
 * @date 2014-09-12 15:30
 */
public class ListLazyAdapter extends BaseAdapter {
    
    private List<Map<String, String>> datas;
    private static LayoutInflater inflater = null;
    
    public ListLazyAdapter(Activity activity, List<Map<String, String>> datas) {
        this.datas = datas;        
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return datas.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    /**
     * 为评论列表中每一行数据设置值,
     * 之前在列表里已经判断过,因此此处不用判断数据是否已经被删除
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null)
            view = inflater.inflate(R.layout.group_list_item, null);
        
        TextView disTitleText = (TextView)view.findViewById(R.id.row_discuss); 			//评论的抬头,比如可能需要显示 查无此人咋办 回复 这个可以有:
        TextView distimeText = (TextView)view.findViewById(R.id.row_discuss_time); 			//评论时间
        TextView disContentText = (TextView)view.findViewById(R.id.row_discuss_content);	//评论的一行                
        ImageView thumbImage = (ImageView)view.findViewById(R.id.row_user_image); 			//头像缩略图
                
        Map<String, String> row = datas.get(position);
        String distime = row.get("distime");
        String content = row.get("content");
        
        //组装设置ListView的相关值
        disTitleText.setText(this.rowTextForResult(row));
        distimeText.setText(distime);
        disContentText.setText(content);

        return view;
    } 
    
    private String rowTextForResult(Map<String, String> row) {
    	StringBuilder res = new StringBuilder();
    	
    	String username = row.get("username");
    	
    	String puid = row.get("puid");
    	String pname = row.get("pname");
    	if(puid != null && Integer.parseInt(puid) == 123){
    		res.append(username).append(":");
    	}else {
           	res.append(username)
           	.append("回复")
           	.append(pname)
           	.append(":");    		
    	}
    	
    	return res.toString();
    }
}
