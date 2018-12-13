package com.example.moe.appversion04.Model;

public class Review {


    private Integer id;
    private String content;
    private Float rating;
    private Integer user_id;
    private Integer post_id;
    private String date_name;

    public Review() {

    }

    public Review(Integer id, String content, Float rating, Integer user_id, Integer post_id, String date_name) {
        this.id = id;
        this.content = content;
        this.rating = rating;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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
