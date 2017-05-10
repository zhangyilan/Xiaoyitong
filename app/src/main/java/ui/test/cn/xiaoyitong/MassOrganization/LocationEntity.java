package ui.test.cn.xiaoyitong.MassOrganization;


import android.graphics.Bitmap;

public class LocationEntity {
    public LocationEntity( String locationName, String activity_name, String activityName) {
        this.locationName = locationName;
        this.activity_name = activity_name;
        this.activitytitleName=activityName;
    }

    public int getImgId() {
        return imgId;
    }

    public String getLocationName() {
        return locationName;
    }

    public Bitmap getActivity_neirong_img() {
        return activity_neirong_img;
    }

    public void setActivity_neirong_img(Bitmap activity_neirong_img) {
        this.activity_neirong_img = activity_neirong_img;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    private Bitmap activity_neirong_img;
    private String activity_name;
    private int imgId;
    private String locationName;
    private String activitytitleName;

    public String getActivitytitleName() {
        return activitytitleName;
    }

    public void setActivitytitleName(String activitytitleName) {
        this.activitytitleName = activitytitleName;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Bitmap getActivity_img() {
        return activity_img;
    }

    public void setActivity_img(Bitmap activity_img) {
        this.activity_img = activity_img;
    }

    private Bitmap activity_img;
}
