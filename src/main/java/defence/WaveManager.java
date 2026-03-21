package defence;

import com.hypixel.hytale.math.vector.Vector3i;

public class WaveManager {
    private int currentWave = 0;
    private GameState gameState;

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }

    public void startWaves(Vector3i pos) {
        gameState = GameState.COUNTDOWN;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
//    public void advanceGameState(GameState gameState) {
//        for(var state : GameState.values()){
//            if(state > gameState) {
//                return true;
//            }
//        }
//    }
}