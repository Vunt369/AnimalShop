package com.example.petshop.model;

public class Contact {
    private String username;
    private String content;
    private String file;
    private String chatRoom;

    public Contact() {
    }

    public Contact(String username, String content, String file, String chatRoom) {
        this.username = username;
        this.content = content;
        this.file = file;
        this.chatRoom = chatRoom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", file='" + file + '\'' +
                ", chatRoom='" + chatRoom + '\'' +
                '}';
    }
}


