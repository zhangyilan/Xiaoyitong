package ui.test.cn.xiaoyitong.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.User;

/**
 * Created by YanChunlin on 2017/4/19.
 */

public class UserUtils {
    /**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     * @return
     */
    public static User getUserInfo(String username){
        User user = MyApplication.getInstance().getContactList().get(username);
        if(user == null){
            user = new User(username);
        }

        if(user != null){
            //demo没有这些数据，临时填充
            user.setNick(username);
        }
        return user;
    }

    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
        User user = getUserInfo(username);
        if(user != null){
            Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
        }
    }
}
