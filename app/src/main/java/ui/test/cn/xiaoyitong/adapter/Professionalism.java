package ui.test.cn.xiaoyitong.adapter;

/**
 * Created by John on 2017/5/25.
 */

public class Professionalism {
    private String imageId;
    private String subject;
    private String department;
    private String finish;
    private String score;
    private String id;
    private String status;

    public Professionalism(String imageId,String subject,String department,String finish,String score,String id,String status){
        this.imageId = imageId;
        this.subject = subject;
        this.department = department;
        this.finish = finish;
        this.score = score;
        this.id = id;
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
}
