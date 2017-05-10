package ui.test.cn.xiaoyitong.Navi.entity;

import android.graphics.Bitmap;

/**
 * Created by renba on 2017/4/19.
 */

public class Address {

    /**
     * address : 一栋
     * X : 30.689214
     * Y : 103.814304
     */

    private String address;
    private String X;
    private String Y;

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    private Bitmap img;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getX() {
        return X;
    }

    public void setX(String X) {
        this.X = X;
    }

    public String getY() {
        return Y;
    }

    public void setY(String Y) {
        this.Y = Y;
    }
}

