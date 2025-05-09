package com.example.mysticmaze.models;

public class RoomMember {
    private int roomId;
    private int userId;
    private String joinedAt;

    public RoomMember(int roomId, int userId, String joinedAt) {
        this.roomId = roomId;
        this.userId = userId;
        this.joinedAt = joinedAt;
    }

    // Getters and Setters
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getJoinedAt() { return joinedAt; }
    public void setJoinedAt(String joinedAt) { this.joinedAt = joinedAt; }
}
