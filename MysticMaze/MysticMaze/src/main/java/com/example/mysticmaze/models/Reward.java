package com.example.mysticmaze.models;

public class Reward {
    private int rewardId;
    private int userId;
    private String rewardType;
    private String description;
    private String awardedAt;

    public Reward(int rewardId, int userId, String rewardType, String description, String awardedAt) {
        this.rewardId = rewardId;
        this.userId = userId;
        this.rewardType = rewardType;
        this.description = description;
        this.awardedAt = awardedAt;
    }

    public int getRewardId() { return rewardId; }
    public void setRewardId(int rewardId) { this.rewardId = rewardId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getRewardType() { return rewardType; }
    public void setRewardType(String rewardType) { this.rewardType = rewardType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAwardedAt() { return awardedAt; }
    public void setAwardedAt(String awardedAt) { this.awardedAt = awardedAt; }
}
