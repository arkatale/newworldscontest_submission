package com.arkatale.defenseplugin.events;

import com.arkatale.defenseplugin.DefensePlugin;
import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.arkatale.defenseplugin.logic.WaveManager;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

//public class WaveStartListener implements EventListener<Player> {
public class WaveStartListener extends EntityEventSystem<EntityStore, UseBlockEvent.Pre> {

    private final DefensePlugin defensePlugin;

    public WaveStartListener(DefensePlugin defensePlugin) {
        super(UseBlockEvent.Pre.class);
        this.defensePlugin = defensePlugin;
    }

    @Override
    public void handle(int i, @NonNull ArchetypeChunk<EntityStore> archetypeChunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer, UseBlockEvent.Pre pre) {
        Universe.get().sendMessage(Message.parse("test"));
        var placeholderPositionToDefend = pre.getTargetBlock();
        //world and entityStore missing/needed
//        defensePlugin.startDefenseAt(placeholderPositionToDefend, world, entityStore);
    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Archetype.empty();
    }
}
