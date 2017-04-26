package com.toddbray.basketballstats;

/**
 * Created by bjordan on 4/19/2017.
 */

public class StatModel {

    private int game_id, player_id, o_rebound, d_rebound, assist, steal, turnover, two_pointer,
            three_pointer, two_pointer_made, three_pointer_made, free_throw, free_throw_made, charge;

    public StatModel() {
        game_id = -99;
        player_id = -99;
    }

    public int getTwo_pointer_made() {
        return two_pointer_made;
    }

    public void setTwo_pointer_made(int two_pointer_made) {
        this.two_pointer_made = two_pointer_made;
    }

    public int getThree_pointer_made() {
        return three_pointer_made;
    }

    public void setThree_pointer_made(int three_pointer_made) {
        this.three_pointer_made = three_pointer_made;
    }

    public int getFree_throw() {
        return free_throw;
    }

    public void setFree_throw(int free_throws) {
        this.free_throw = free_throws;
    }

    public int getFree_throw_made() {
        return free_throw_made;
    }

    public void setFree_throw_made(int free_throws_made) {
        this.free_throw_made = free_throws_made;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charges) {
        this.charge = charges;
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
}
