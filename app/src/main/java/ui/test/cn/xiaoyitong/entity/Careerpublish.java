package ui.test.cn.xiaoyitong.entity;

import android.graphics.Bitmap;

/**
 * Created by lenovo on 2017/06/03.
 */

public class Careerpublish {
    private int Id;
    private String publish_branch;
    private String quality_frade;
    private String theme;
    private String include;
    private String publish_time;
    private String start_time;
    private String end_time;
    private String activity_address;
    private String activity_type;
    private String activity_background;
    private String activity_suggest;
    private String status;
    private Bitmap imgBitmap;

    public Careerpublish() {

    }

    public Careerpublish(String theme, String publish_branch) {
        this.theme = theme;
        this.publish_branch = publish_branch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPublish_branch() {
        return publish_branch;
    }

    public void setPublish_branch(String publish_branch) {
        this.publish_branch = publish_branch;
    }

    public String getQuality_frade() {
        return quality_frade;
    }

    public void setQuality_frade(String quality_frade) {
        this.quality_frade = quality_frade;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getActivity_address() {
        return activity_address;
    }

    public void setActivity_address(String activity_address) {
        this.activity_address = activity_address;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public String getActivity_background() {
        return activity_background;
    }

    public void setActivity_background(String activity_background) {
        this.activity_background = activity_background;
    }

    public String getActivity_suggest() {
        return activity_suggest;
    }

    public void setActivity_suggest(String activity_suggest) {
        this.activity_suggest = activity_suggest;
    }
}
