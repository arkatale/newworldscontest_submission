package com.arkatale.defenseplugin.systems;

import com.arkatale.defenseplugin.components.AttackerComponent;
import com.arkatale.defenseplugin.components.DefendBlockComponent;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.spatial.SpatialResource;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class DefendBlockSystem extends EntityTickingSystem<ChunkStore> {
    @Override
    public void tick(float v, int i, @NonNull ArchetypeChunk<ChunkStore> archetypeChunk, @NonNull Store<ChunkStore> store, @NonNull CommandBuffer<ChunkStore> commandBuffer) {
        var defendBlock = archetypeChunk.getComponent(i, DefendBlockComponent.getComponentType());

        if(defendBlock == null){
            var t = "";
            return;
        }

        var toDefendPosition = defendBlock.getPositionInWorld();

        if(toDefendPosition == null) return;

        var world = commandBuffer.getExternalData().getWorld();

        world.execute(() -> {
                   var pos = toDefendPosition.toVector3d();
                   var entityStore = world.getEntityStore();
                    SpatialResource<Ref<EntityStore>, EntityStore> spatial = entityStore.getStore().getResource(EntityModule.get().getEntitySpatialResourceType());
                        List<Ref<EntityStore>> entities = SpatialResource.getThreadLocalReferenceList();
//                    spatial.getSpatialStructure().collect(toDefendPosition, 3, players);
                    spatial.getSpatialStructure().collect(pos, 3d, entities);

                    for(var entity : entities){
//                        todo check if has AttackerComponent - at later time if that works then
//                        var store = entity.getStore();
//                        store.getComponent(entity, NPCComp)
                        NPCEntity npcComponent = entityStore.getStore().getComponent(entity, Objects.requireNonNull(NPCEntity.getComponentType()));
//                        fix by using store of entity store instead of chunkstore param Inferred type 'T' for type parameter 'T' is not within its bound; should implement 'com.hypixel.hytale.component.Component<com.hypixel.hytale.server.core.universe.world.storage.ChunkStore>'
                        if(npcComponent == null) continue;
//                        Universe.get().sendMessage(Message.raw("corruption +1"));
                        defendBlock.setCorruption(defendBlock.getCorruption()+1);

                    }

                    if(defendBlock.getCorruption() > 10){
//                        Universe.get().sendMessage(Message.raw("You loose"));
                        return;
                    }

                });

        var test = "";
    }

    @Override
    public @Nullable Query<ChunkStore> getQuery() {
        return Query.and(DefendBlockComponent.getComponentType());
    }
}
