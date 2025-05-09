package com.example.mysticmaze.models;

public class PuzzleHint {
    private int hintId;
    private int puzzleId;
    private String hintText;
    private int level;

    public PuzzleHint(int hintId, int puzzleId, String hintText, int level) {
        this.hintId = hintId;
        this.puzzleId = puzzleId;
        this.hintText = hintText;
        this.level = level;
    }

    // Getters and Setters
    public int getHintId() { return hintId; }
    public void setHintId(int hintId) { this.hintId = hintId; }
    public int getPuzzleId() { return puzzleId; }
    public void setPuzzleId(int puzzleId) { this.puzzleId = puzzleId; }
    public String getHintText() { return hintText; }
    public void setHintText(String hintText) { this.hintText = hintText; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
