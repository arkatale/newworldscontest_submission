package com.arkatale.defenseplugin.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import org.jspecify.annotations.Nullable;

public class DefendBlockComponent implements Component<ChunkStore> {
    @Override
    public @Nullable Component<ChunkStore> clone() {
        return null;
    }
}
