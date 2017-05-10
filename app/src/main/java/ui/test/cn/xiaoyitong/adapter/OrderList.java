package ui.test.cn.xiaoyitong.adapter;

/**
 * Created by John on 2017/4/21.
 */

public class OrderList {
    private String imageUrl;
    private String type;
    private String orderTime;
    private String status;
    private String id;

    public OrderList(String imageUrl, String type, String orderTime, String status, String id){
        this.imageUrl = imageUrl;
        this.type = type;
        this.orderTime = orderTime;
        this.status = status;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
}
