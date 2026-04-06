package com.arkatale.defenseplugin.util;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.spatial.SpatialResource;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.List;

public class Entities {
    public void removeEntitiesInRadius(World world, Vector3d pos, double radius){
        var entityStore = world.getEntityStore();
        var store = entityStore.getStore();
        SpatialResource<Ref<EntityStore>, EntityStore> spatial = entityStore.getStore().getResource(EntityModule.get().getEntitySpatialResourceType());
        List<Ref<EntityStore>> entities = SpatialResource.getThreadLocalReferenceList();
        spatial.getSpatialStructure().collect(pos, radius, entities);

        for(Ref<EntityStore> entityRef : entities) {
            try{
                store.removeEntity(entityRef, RemoveReason.UNLOAD);

            }catch (Exception e){
                HytaleLogger.getLogger().atSevere().log("Couldn't remove because " + e.getMessage());
            }

        }
    }
}
