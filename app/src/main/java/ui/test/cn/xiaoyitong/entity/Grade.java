package ui.test.cn.xiaoyitong.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/18.
 */

public class Grade implements Serializable{
    private String course_name;
    private String course_grade;
    private String grade_point;

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_grade() {
        return course_grade;
    }

    public void setCourse_grade(String course_grade) {
        this.course_grade = course_grade;
    }

    public String getGrade_point() {
        return grade_point;
    }

    public void setGrade_point(String grade_point) {
        this.grade_point = grade_point;
    }
}
