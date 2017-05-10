package ui.test.cn.xiaoyitong.adapter;

/**
 * Created by John on 2017/4/15.
 */

public class ReleaseDetails {
    private String imageUrl;//根据这个值，判断加载的图片
    private String nickName;//昵称
    private String name;//真实姓名
    private String phoneNumber;//电话号码
    private String pickNumber;//取货码
    private String address;//收货地址
    private String urgent;//紧急程度
    private String expectationtime;//送达时间
    private String type;//快递类型

    public ReleaseDetails(String imageUrl,String nickName,String name,String phoneNumber,String pickNumber,String address,String urgent,String expectationtime,String type){
        this.imageUrl = imageUrl;
        this.nickName = nickName;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pickNumber = pickNumber;
        this.address = address;
        this.urgent = urgent;
        this.expectationtime = expectationtime;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getExpectationtime() {
        return expectationtime;
    }

    public String getImageId() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPickNumber() {
        return pickNumber;
    }

    public String getUrgent() {
        return urgent;
    }
}
