package com.wizz.seckill.Model;

public class actInfo {
    private String imgurl;
    private int theme;
    private String name;
    private String des;
    private String textdetail;
    private int ownerid;
    // auto_increment
    private int actid;

    public actInfo() {
    }

    public actInfo(String imgurl, int theme, String name, String des, String textdetail, int ownerid) {
        this.imgurl = imgurl;
        this.theme = theme;
        this.name = name;
        this.des = des;
        this.textdetail = textdetail;
        this.ownerid = ownerid;
    }


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTextdetail() {
        return textdetail;
    }

    public void setTextdetail(String textdetail) {
        this.textdetail = textdetail;
    }

    public int getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(int ownerid) {
        this.ownerid = ownerid;
    }

    public int getActid() {
        return actid;
    }

    public void setActid(int actid) {
        this.actid = actid;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }
}
