package ui.test.cn.xiaoyitong.adapter;

/**
 * Created by John on 2017/4/18.
 */

public class CloudList {
    private String id;
    private String imageUrl;
    private String title;
    private String serviceTime;
    private String price;

    public CloudList(String id,String imageUrl,String title,String serviceTime,String price){
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.serviceTime = serviceTime;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public String getTitle() {
        return title;
    }
}
