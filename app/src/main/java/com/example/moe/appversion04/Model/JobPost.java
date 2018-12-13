package com.example.moe.appversion04.Model;

public class JobPost {

    private String title;
    private String description;
    private String price;
    private String location;

    private String rating;
    private String imageUri;

    private int userId;
    private int id;
    private String dateItemAdded;

    private int postUserId;

    private int category_id;
    private int subcategory_id;

    public JobPost(){

    }

    public JobPost(String title, String description, String price, String location, String rating, String imageUri) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.rating = rating;
        this.imageUri = imageUri;
    }

    public JobPost(String title, String description, String price, String location, String dateItemAdded, String imageUri, int category_id, int subcategory_id) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.dateItemAdded = dateItemAdded;
        this.imageUri = imageUri;
        this.category_id = category_id;
        this.subcategory_id = subcategory_id;
    }


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(int postUserId) {
        this.postUserId = postUserId;
    }
}
