package ui.test.cn.xiaoyitong.adapter;

/**
 * Created by John on 2017/4/19.
 */

public class CloudDetailedList {
    private String imageUrl;
    private String title;
    private String nickname;
    private String phoneNumber;
    private String paginalNumber;
    private String address;
    private String type;
    private String servicetime;
    private String color;
    private String univalent;
    private String total;

    public CloudDetailedList(String imageUrl,String title, String nickname, String phoneNumber, String paginalNumber, String address, String type, String servicetime, String color, String univalent,String total){
        this.imageUrl = imageUrl;
        this.title = title;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.paginalNumber = paginalNumber;
        this.address = address;
        this.type = type;
        this.servicetime = servicetime;
        this.color = color;
        this.univalent = univalent;
        this.total = total;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getColor() {
        return color;
    }


    public String getNickname() {
        return nickname;
    }

    public String getPaginalNumber() {
        return paginalNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getServicetime() {
        return servicetime;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUnivalent() {
        return univalent;
    }

    public String getTotal() {
        return total;
    }
}
