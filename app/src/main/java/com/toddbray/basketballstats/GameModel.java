package com.toddbray.basketballstats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bjordan on 4/19/2017.
 */

public class GameModel {

    private int game_id;
    private int season_id;
    private Date game_date, girls_jv, boys_jv, girls_v, boys_v;
    private String opp_name;
    private String location;
    private String venue;

    public GameModel() {
        game_id = -99;
        season_id = -99;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public Date getGame_date() {
        return game_date;
    }

    public void setGame_date(Date game_date) {
        this.game_date = game_date;
    }

    public Date getGirls_jv() {
        return girls_jv;
    }

    public void setGirls_jv(Date girls_jv) {
        this.girls_jv = girls_jv;
    }

    public Date getBoys_jv() {
        return boys_jv;
    }

    public void setBoys_jv(Date boys_jv) {
        this.boys_jv = boys_jv;
    }

    public Date getGirls_v() {
        return girls_v;
    }

    public void setGirls_v(Date girls_v) {
        this.girls_v = girls_v;
    }

    public Date getBoys_v() {
        return boys_v;
    }

    public void setBoys_v(Date boys_v) {
        this.boys_v = boys_v;
    }

    public String getOpp_name() {
        return opp_name;
    }

    public void setOpp_name(String opp_name) {
        this.opp_name = opp_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("MM/dd");
        String tmp = df.format(game_date);
        tmp += "   " + opp_name;
        if (tmp.length() > 40) {
            tmp = tmp.substring(0, 40) + "...";
        }
        return tmp;
    }
}
