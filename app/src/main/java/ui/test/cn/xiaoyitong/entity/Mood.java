package ui.test.cn.xiaoyitong.entity;

/**
 * Created by YanChunlin on 2017/4/25.
 */

public class Mood {
    private String name;
    private String time;
    private int img;
    private String content;

    public Mood() {
    }

    public Mood(String name, String time, int img, String content) {
        this.name = name;
        this.time = time;
        this.img = img;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
