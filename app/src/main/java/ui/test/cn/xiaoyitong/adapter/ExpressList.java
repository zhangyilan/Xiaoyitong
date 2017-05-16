package ui.test.cn.xiaoyitong.adapter;

/**
 * Created by John on 2017/4/14.
 */

public class ExpressList {
    private String imageId;
    private String id;
    private String expectationTime;
    private String releaseTime;
    private String type;
    private String userId;

    public ExpressList(String imageId,String id,String type,String expectationTime,String releaseTime,String userId){
        this.imageId = imageId;
        this.id = id;
        this.releaseTime = releaseTime;
        this.expectationTime = expectationTime;
        this.type = type;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getExpectationTime() {
        return expectationTime;
    }

    public String getImageId() {
        return imageId;
    }

    public String getType() {
        return type;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getUserId() {
        return userId;
    }
}
