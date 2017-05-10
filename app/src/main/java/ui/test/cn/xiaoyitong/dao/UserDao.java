package ui.test.cn.xiaoyitong.dao;

import android.content.Context;

import java.util.List;
import java.util.Map;

import ui.test.cn.xiaoyitong.db.DemoDBManager;
import ui.test.cn.xiaoyitong.entity.User;


/**
 * Created by YanChunlin on 2017/4/19.
 */

public class UserDao {
    public static final String TABLE_NAME = "uers";
    public static final String COLUMN_NAME_ID = "username";
    public static final String COLUMN_NAME_NICK = "nick";
    public static final String COLUMN_NAME_AVATAR = "avatar";

    public static final String PREF_TABLE_NAME = "pref";
    public static final String COLUMN_NAME_DISABLED_GROUPS = "disabled_groups";
    public static final String COLUMN_NAME_DISABLED_IDS = "disabled_ids";

    public UserDao(Context context) {
        DemoDBManager.getInstance().onInit(context);
    }

    /**
     * 保存好友list
     *
     * @param contactList
     */
    public void saveContactList(List<User> contactList) {
        DemoDBManager.getInstance().saveContactList(contactList);
    }

    /**
     * 获取好友list
     *
     * @return
     */
    public Map<String, User> getContactList() {

        return DemoDBManager.getInstance().getContactList();
    }

    /**
     * 删除一个联系人
     * @param username
     */
    public void deleteContact(String username){
        DemoDBManager.getInstance().deleteContact(username);
    }

    /**
     * 保存一个联系人
     * @param user
     */
    public void saveContact(User user){
        DemoDBManager.getInstance().saveContact(user);
    }

    public void setDisabledGroups(List<String> groups){
        DemoDBManager.getInstance().setDisabledGroups(groups);
    }

    public List<String>  getDisabledGroups(){
        return DemoDBManager.getInstance().getDisabledGroups();
    }

    public void setDisabledIds(List<String> ids){
        DemoDBManager.getInstance().setDisabledIds(ids);
    }

    public List<String> getDisabledIds(){
        return DemoDBManager.getInstance().getDisabledIds();
    }
}

