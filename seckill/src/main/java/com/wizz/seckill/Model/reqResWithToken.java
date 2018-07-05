package com.wizz.seckill.Model;

public class reqResWithToken {
    private Integer result;
    private String token;
    private int id;


    public reqResWithToken(Integer result, String token, int id) {
        this.result = result;
        this.token = token;
        this.id = id;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

