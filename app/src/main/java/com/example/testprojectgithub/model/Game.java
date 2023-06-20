package com.example.testprojectgithub.model;

import java.util.Date;

public class Game {
    private int idGame;
    private String gameName;
    private String gameDescription;
    private String gameRating;
    private Date date;

    public Game(){

    }

    public Game(int idGame, String gameName, String gameDescription, String gameRating, Date date) {
        this.idGame = idGame;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gameRating = gameRating;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Game{" +
                "idGame=" + idGame +
                ", gameName='" + gameName + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", gameRating='" + gameRating + '\'' +
                ", date=" + date +
                '}';
    }
}
