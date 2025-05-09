package com.example.mysticmaze.models;

public class Room {
    private int roomId;
    private String roomCode;
    private int hostUserId;
    private String status;
    private String createdAt;

    public Room(int roomId, String roomCode, int hostUserId, String status, String createdAt) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.hostUserId = hostUserId;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }
    public int getHostUserId() { return hostUserId; }
    public void setHostUserId(int hostUserId) { this.hostUserId = hostUserId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
