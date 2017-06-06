package ui.test.cn.xiaoyitong.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/06/05.
 */

public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String name;
    private  String usernum;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
