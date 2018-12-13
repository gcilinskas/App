package com.example.moe.appversion04.Model;

public class Message implements Comparable<Message> {
    private int id;
    private String content;
    private int user_id;
    private int receiver_id;
    private String dateAdded;


    public Message(int id, String content, int user_id, int receiver_id, String dateAdded) {
        this.id = id;
        this.content = content;
        this.user_id = user_id;
        this.receiver_id = receiver_id;
        this.dateAdded = dateAdded;
    }

    public Message(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    @Override
    public int compareTo(Message o) {
        return getDateAdded().compareTo(o.getDateAdded());
    }



}
