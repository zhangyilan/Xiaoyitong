package ui.test.cn.xiaoyitong.MassOrganization.entity;

import android.graphics.Bitmap;

/**
 * Created by renba on 2017/4/18.
 */

public class Organization {
    private String name;

    public String getMassage() {
        return massage;
    }
    public void setMassage(String massage) {
        this.massage = massage;
    }

    private String massage;

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    private Bitmap img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
