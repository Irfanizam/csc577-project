package com.example.testprojectgithub.model;

public class Review {
    private int reviewId;
    private int gameId;
    private int userId;
    private String comment;

    public Review() {
        // Default constructor
    }

    public Review(int reviewId, int gameId,int userId,String comment) {
        this.reviewId = reviewId;
        this.gameId = gameId;
        this.userId = userId;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", gameId=" + gameId +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
