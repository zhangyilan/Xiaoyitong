package ui.test.cn.xiaoyitong.dao;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import ui.test.cn.xiaoyitong.db.DemoDBManager;
import ui.test.cn.xiaoyitong.entity.InviteMessage;


/**
 * Created by YanChunlin on 2017/4/19.
 */

public class InviteMessgeDao {
    public static final String TABLE_NAME = "new_friends_msgs";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_FROM = "username";
    public static final String COLUMN_NAME_GROUP_ID = "groupid";
    public static final String COLUMN_NAME_GROUP_Name = "groupname";

    public static final String COLUMN_NAME_TIME = "time";
    public static final String COLUMN_NAME_REASON = "reason";
    public static final String COLUMN_NAME_STATUS = "status";
    public static final String COLUMN_NAME_ISINVITEFROMME = "isInviteFromMe";

    public InviteMessgeDao(Context context){
        DemoDBManager.getInstance().onInit(context);
    }

    /**
     * 保存message
     * @param message
     * @return  返回这条messaged在db中的id
     */
    public Integer saveMessage(InviteMessage message){
        return DemoDBManager.getInstance().saveMessage(message);
    }

    /**
     * 更新message
     * @param msgId
     * @param values
     */
    public void updateMessage(int msgId,ContentValues values){
        DemoDBManager.getInstance().updateMessage(msgId, values);
    }

    /**
     * 获取messges
     * @return
     */
    public List<InviteMessage> getMessagesList(){
        return DemoDBManager.getInstance().getMessagesList();
    }

    public void deleteMessage(String from){
        DemoDBManager.getInstance().deleteMessage(from);
    }
}

