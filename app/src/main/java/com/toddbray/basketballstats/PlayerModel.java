package com.toddbray.basketballstats;

/**
 * Created by bjordan on 4/19/2017.
 */

public class PlayerModel {

    private int player_id, number;
    private String first_name, last_name, year;

    public PlayerModel() {

    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        String tmp = first_name + last_name;
        if (tmp.length() > 20) {
            tmp = tmp.substring(0, 20) + "...";
        }
        return tmp;
    }
}
