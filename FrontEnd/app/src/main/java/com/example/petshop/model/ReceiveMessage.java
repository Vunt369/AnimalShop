package com.example.petshop.model;

public class ReceiveMessage {
    private String username;
    private int userId;
    private String mess;

    private String chatRoom;

    private String filePath;

    public ReceiveMessage(String username, String mess, String filePath) {
        this.username = username;
        this.mess = mess;
        this.filePath = filePath;
    }

    public ReceiveMessage() {
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
