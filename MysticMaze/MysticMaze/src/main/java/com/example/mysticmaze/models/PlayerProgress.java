package com.example.mysticmaze.models;

public class PlayerProgress {
    private int sessionId;
    private int userId;
    private int puzzleId;
    private int level;
    private String startTime;
    private String endTime;
    private String status;
    private boolean isFirstSolver;

    public PlayerProgress(int sessionId, int userId, int puzzleId, int level, String startTime, String endTime, String status, boolean isFirstSolver) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.puzzleId = puzzleId;
        this.level = level;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.isFirstSolver = isFirstSolver;
    }

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getPuzzleId() { return puzzleId; }
    public void setPuzzleId(int puzzleId) { this.puzzleId = puzzleId; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isFirstSolver() { return isFirstSolver; }
    public void setFirstSolver(boolean firstSolver) { isFirstSolver = firstSolver; }
}

