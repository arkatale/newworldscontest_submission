package com.arkatale.defenseplugin.events;

import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import defence.WaveManager;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

//public class WaveStartListener implements EventListener<Player> {
public class WaveStartListener extends EntityEventSystem<EntityStore, UseBlockEvent.Pre> {

    public WaveStartListener(WaveManager waveManager) {
        super(UseBlockEvent.Pre.class);
    }

    @Override
    public void handle(int i, @NonNull ArchetypeChunk<EntityStore> archetypeChunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer, UseBlockEvent.Pre pre) {

    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Archetype.empty();
    }
}
