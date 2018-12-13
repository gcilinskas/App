package com.example.moe.appversion04.Model;

public class Subcategory {

    private String name;
    private int category_id;
    private int id;

    public Subcategory() {

    }

    public Subcategory(String name, int category_id) {
        this.name = name;
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
