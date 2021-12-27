package com.epl.blog.models;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Statistics() {
    }

    public Statistics(int place, String team, int game, int win, int draw, int losing, String balls, int points, String percentagePoints) {
        this.place = place;
        this.team = team;
        this.balls = balls;
        this.game = game;
        this.win = win;
        this.draw = draw;
        this.losing = losing;
        this.points = points;
        this.percentagePoints = percentagePoints;
    }

    private String team, balls, percentagePoints;

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    private int place, game, win, draw, losing, points;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }


    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLosing() {
        return losing;
    }

    public void setLosing(int losing) {
        this.losing = losing;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPercentagePoints() {
        return percentagePoints;
    }

    public void setPercentagePoints(String percentagePoints) {
        this.percentagePoints = percentagePoints;
    }


}
