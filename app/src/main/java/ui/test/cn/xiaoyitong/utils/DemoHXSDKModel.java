package ui.test.cn.xiaoyitong.utils;

import android.content.Context;

import java.util.List;
import java.util.Map;

import ui.test.cn.xiaoyitong.dao.UserDao;
import ui.test.cn.xiaoyitong.db.DemoDBManager;
import ui.test.cn.xiaoyitong.entity.User;
import ui.test.cn.xiaoyitong.model.DefaultHXSDKModel;


/**
 * Created by YanChunlin on 2017/4/19.
 */

public class DemoHXSDKModel extends DefaultHXSDKModel {

    public DemoHXSDKModel(Context ctx) {
        super(ctx);
    }

    public boolean getUseHXRoster() {
        return false;
    }

    public boolean isDebugMode(){
        return true;
    }

    public boolean saveContactList(List<User> contactList) {
        UserDao dao = new UserDao(context);
        dao.saveContactList(contactList);
        return true;
    }

    public Map<String, User> getContactList() {
        UserDao dao = new UserDao(context);
        return dao.getContactList();
    }

    public void closeDB() {
        DemoDBManager.getInstance().closeDB();
    }

    @Override
    public String getAppProcessName() {
        return context.getPackageName();
    }
}

