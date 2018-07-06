package com.wizz.seckill.Model;

public class reqRes {
    private Integer result;
    private String des;

    public reqRes(Integer result, String des) {
        this.result = result;
        this.des = des;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getResult() {
        return result;
    }

    public String getDes() {
        return des;
    }
}
