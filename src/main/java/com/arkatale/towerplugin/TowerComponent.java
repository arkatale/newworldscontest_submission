package com.arkatale.towerplugin;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.Nullable;

public class TowerComponent implements Component<EntityStore> {

    private int score = 0;

    public TowerComponent(TowerComponent towerComponent) {
        this.setScore(towerComponent.getScore());
    }

    @Override
    public @Nullable Component<EntityStore> clone() {
        return new TowerComponent(this);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
