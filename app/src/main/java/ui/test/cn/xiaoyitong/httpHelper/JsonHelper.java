package ui.test.cn.xiaoyitong.httpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/4/13.
 */

public class JsonHelper {

    /**
     * 解析数据的方法
     */
    public static List<String> jsonjiesi(String data){
         List<String> strings = new ArrayList<>();
        String s = "";
        try {
            JSONArray jsonArray=new JSONArray(data);
            for (int a=0;a<jsonArray.length();a++){
                JSONObject jsonObject=jsonArray.getJSONObject(a);
                s=jsonObject.getString("newstitle");
                strings.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
