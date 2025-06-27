// File: Message.java
package com.example.mysticmaze.models;

public class Message {
    private String type;     // e.g., "chat", "progress", "join"
    private String sender;   // username
    private String roomId;   // room/team name
    private String content;  // message text or progress info

    public Message(String type, String sender, String roomId, String content) {
        this.type = type;
        this.sender = sender;
        this.roomId = roomId;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getContent() {
        return content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessage() {
        return "Message";

    }
}
