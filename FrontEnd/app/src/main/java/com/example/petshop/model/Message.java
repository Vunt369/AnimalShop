package com.example.petshop.model;

public class Message {
    private String content;
    private String file;
    private long teamId;
    private Talker sender;
    private Talker receiver;
    private String date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public Talker getSender() {
        return sender;
    }

    public void setSender(Talker sender) {
        this.sender = sender;
    }

    public Talker getReceiver() {
        return receiver;
    }

    public void setReceiver(Talker receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
