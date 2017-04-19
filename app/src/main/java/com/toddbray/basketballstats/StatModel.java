package com.toddbray.basketballstats;

/**
 * Created by bjordan on 4/19/2017.
 */

public class StatModel {

    private int game_id, player_id, shot, o_rebound, d_rebound, assist, steal, turnover, two_pointer, three_pointer, dunk;

    public StatModel() {

    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getShot() {
        return shot;
    }

    public void setShot(int shot) {
        this.shot = shot;
    }

    public int getO_rebound() {
        return o_rebound;
    }

    public void setO_rebound(int o_rebound) {
        this.o_rebound = o_rebound;
    }

    public int getD_rebound() {
        return d_rebound;
    }

    public void setD_rebound(int d_rebound) {
        this.d_rebound = d_rebound;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getSteal() {
        return steal;
    }

    public void setSteal(int steal) {
        this.steal = steal;
    }

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

    public int getTwo_pointer() {
        return two_pointer;
    }

    public void setTwo_pointer(int two_pointer) {
        this.two_pointer = two_pointer;
    }

    public int getThree_pointer() {
        return three_pointer;
    }

    public void setThree_pointer(int three_pointer) {
        this.three_pointer = three_pointer;
    }

    public int getDunk() {
        return dunk;
    }

    public void setDunk(int dunk) {
        this.dunk = dunk;
    }
}
