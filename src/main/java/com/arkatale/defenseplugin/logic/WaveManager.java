package com.arkatale.defenseplugin.logic;

import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.universe.world.World;

public class WaveManager {
    private int currentWave = 0;
    private GameState gameState;
    private World world;

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }

    public boolean startWaves(Vector3i pos, World world) {
        if(gameState == null){
            this.world = world;
            gameState = GameState.COUNTDOWN;
            return true;
        }

        return false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void spawnWave(){
        for (int i = 0; i < 10; i++){
//            world.spawnEntity()
        }
    }
}