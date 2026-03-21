package com.arkatale.defenseplugin.logic;

public class TowerPowerSystem {
    public boolean isBoss(){
         return power >= 10;
        }

    private int power;
    public void addPowerFromEnemy(){
        power++;
    }

    /** Todo in implementation (on call) don't remove ... if returns false */
    public boolean healTower(int amount){
        if(power <= 0) return false; 
        power = Math.max(0, power - amount);
        return true;
    }
}