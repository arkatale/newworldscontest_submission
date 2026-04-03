package com.arkatale.defenseplugin.systems;

import com.arkatale.defenseplugin.components.AttackerComponent;
import com.arkatale.defenseplugin.components.DefendBlockComponent;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class DefendBlockSystem extends EntityTickingSystem<ChunkStore> {
    @Override
    public void tick(float v, int i, @NonNull ArchetypeChunk<ChunkStore> archetypeChunk, @NonNull Store<ChunkStore> store, @NonNull CommandBuffer<ChunkStore> commandBuffer) {
        var defendBlock = archetypeChunk.getComponent(i, DefendBlockComponent.getComponentType());

        if(defendBlock == null){
            var t = "";
            return;
        }

        var test = "";
    }

    @Override
    public @Nullable Query<ChunkStore> getQuery() {
        return Query.and(DefendBlockComponent.getComponentType());
    }
}
