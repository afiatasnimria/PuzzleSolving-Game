package com.example.mysticmaze.models;

public class Leaderboard {
    private int sessionId;
    private int roomId;
    private int totalTime;
    private int totalHintsUsed;
    private double accuracyScore;
    private int rank;

    public Leaderboard(int sessionId, int roomId, int totalTime, int totalHintsUsed, double accuracyScore, int rank) {
        this.sessionId = sessionId;
        this.roomId = roomId;
        this.totalTime = totalTime;
        this.totalHintsUsed = totalHintsUsed;
        this.accuracyScore = accuracyScore;
        this.rank = rank;
    }

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public int getTotalTime() { return totalTime; }
    public void setTotalTime(int totalTime) { this.totalTime = totalTime; }
    public int getTotalHintsUsed() { return totalHintsUsed; }
    public void setTotalHintsUsed(int totalHintsUsed) { this.totalHintsUsed = totalHintsUsed; }
    public double getAccuracyScore() { return accuracyScore; }
    public void setAccuracyScore(double accuracyScore) { this.accuracyScore = accuracyScore; }
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }
}

