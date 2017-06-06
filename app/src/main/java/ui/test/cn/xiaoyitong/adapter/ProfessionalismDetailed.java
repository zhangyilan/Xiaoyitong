package ui.test.cn.xiaoyitong.adapter;

/**
 * Created by John on 2017/5/28.
 */

public class ProfessionalismDetailed {
    private String imgUrl;
    private String title;
    private String status;
    private String value;
    private String startTime;
    private String endTime;
    private String address;
    private String professionalismClass;
    private String department;
    private String content;
    private String activityBG;

    public ProfessionalismDetailed(String imgUrl,String title,String status,String value,String startTime,
                                   String endTime,String address,String professionalismClass,String department,String content,String activityBG){
        this.imgUrl = imgUrl;
        this.title = title;
        this.status = status;
        this.value = value;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.professionalismClass = professionalismClass;
        this.department = department;
        this.content = content;
        this.activityBG = activityBG;
    }

    public String getAddress() {
        return address;
    }

    public String getContent() {
        return content;
    }

    public String getDepartment() {
        return department;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getProfessionalismClass() {
        return professionalismClass;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public String getActivityBG() {
        return activityBG;
    }
}
