package com.wizz.seckill.Model;

public class actAtr {
    private String begtime;
    private String endtime;
    private String seckilltime;
    private int tickets;
    private int ownerid;
    //auto_inc
    private int actid;

    public actAtr() {
    }

    public actAtr(String begtime, String endtime, String seckilltime, int tickets, int ownerid) {
        this.begtime = begtime;
        this.endtime = endtime;
        this.seckilltime = seckilltime;
        this.tickets = tickets;
        this.ownerid = ownerid;
    }

    public String getBegtime() {
        return begtime;
    }

    public void setBegtime(String begtime) {
        this.begtime = begtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getSeckilltime() {
        return seckilltime;
    }

    public void setSeckilltime(String seckilltime) {
        this.seckilltime = seckilltime;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
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
}
