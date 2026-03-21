package com.arkatale.defenseplugin.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.Nullable;

public class AttackerComponent implements Component<EntityStore> {
    @Override
    public @Nullable Component<EntityStore> clone() {
        return null;
    }
}
