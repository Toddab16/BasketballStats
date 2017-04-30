package com.toddbray.basketballstats;

/**
 * Created by bjordan on 4/25/2017.
 */

public class SeasonModel {
    private int season_id, season_name;
    private String android_id;

    public SeasonModel(String android_id) {
        season_id = MySqlLiteHelper.NEW_ROW;
        this.android_id = android_id;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getSeason_name() {
        return season_name;
    }

    public void setSeason_name(int season_name) {
        this.season_name = season_name;
    }
}
