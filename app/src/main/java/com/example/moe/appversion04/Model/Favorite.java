package com.example.moe.appversion04.Model;

public class Favorite {

    private Integer id;
    private Integer user_id;
    private Integer post_id;
    private String date_name;

    public Favorite(){

    }

    public Favorite(Integer id, Integer user_id, Integer post_id, String date_name) {
        this.id = id;
        this.user_id = user_id;
        this.post_id = post_id;
        this.date_name = date_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public String getDate_name() {
        return date_name;
    }

    public void setDate_name(String date_name) {
        this.date_name = date_name;
    }
}
