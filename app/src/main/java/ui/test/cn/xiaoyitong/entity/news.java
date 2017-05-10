package ui.test.cn.xiaoyitong.entity;

import android.graphics.Bitmap;

import java.util.Set;

/**
 * Created by lenovo on 2017/04/13.
 */

public class news {
    private int id; // id
    private String title; // 标题
    private String content; // 文章内容简介
    private String newsHref; //url
    private String source; //来源
    private String postime; //发表时间
    private String author; //作者
    private int read;//阅读次数
    private Bitmap imgBitmap;//阅读次数
    private Set<Bitmap> set;

    public Set<Bitmap> getSet() {
        return set;
    }

    public void setSet(Set<Bitmap> set) {
        this.set = set;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNewsHref() {
        return newsHref;
    }

    public void setNewsHref(String newsHref) {
        this.newsHref = newsHref;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPostime() {
        return postime;
    }

    public void setPostime(String postime) {
        this.postime = postime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
