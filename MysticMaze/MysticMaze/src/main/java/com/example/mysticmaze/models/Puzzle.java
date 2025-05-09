package com.example.mysticmaze.models;

public class Puzzle {
    private int puzzleId;
    private int level;
    private String type;
    private String puzzleData;
    private String solution;

    public Puzzle(int puzzleId, int level, String type, String puzzleData, String solution) {
        this.puzzleId = puzzleId;
        this.level = level;
        this.type = type;
        this.puzzleData = puzzleData;
        this.solution = solution;
    }

    // Getters and Setters
    public int getPuzzleId() { return puzzleId; }
    public void setPuzzleId(int puzzleId) { this.puzzleId = puzzleId; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getPuzzleData() { return puzzleData; }
    public void setPuzzleData(String puzzleData) { this.puzzleData = puzzleData; }
    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }
}
