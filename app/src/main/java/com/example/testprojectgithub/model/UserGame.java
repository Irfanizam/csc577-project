package com.example.testprojectgithub.model;

public class UserGame {
    private int idGame;
    private String gameName;
    private String gameDescription;
    private String gameRating;
    private String releaseDate;
    private String gameUserRating;
    private String gameUserReview;

    public UserGame(){

    }

    public UserGame(int idGame, String gameName, String gameDescription, String gameRating, String releaseDate, String gameUserRating, String gameUserReview) {
        this.idGame = idGame;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gameRating = gameRating;
        this.releaseDate = releaseDate;
        this.gameUserRating = gameUserRating;
        this.gameUserReview = gameUserReview;
    }
    public UserGame(int i, String gameName, String gameDescription, String gameRating, String release_at) {
    }


    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getGameRating() {
        return gameRating;
    }

    public void setGameRating(String gameRating) {
        this.gameRating = gameRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getGameUserRating() {
        return gameUserRating;
    }

    public void setGameUserRating(String gameUserRating) {
        this.gameRating = gameRating;
    }
    public String getGameUserReview() {
        return gameUserReview;
    }

    public void setGameUserReview(String gameUserReview) {
        this.gameRating = gameUserReview;
    }

    @Override
    public String toString() {
        return "UserGame{" +
                "idGame=" + idGame +
                ", gameName='" + gameName + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", gameRating='" + gameRating + '\'' +
                ", =" + releaseDate +
                ", gameUserRating='" + gameUserRating + '\'' +
                ", gameUserReview='" + gameUserReview + '\'' +
                '}';
    }


}

