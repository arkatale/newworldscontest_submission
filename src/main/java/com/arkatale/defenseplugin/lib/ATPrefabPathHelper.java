//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
// from \HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathHelper.class
// with modification to return PatrolPathMarkerEntity and name change to ATPrefabPathHelper

package com.arkatale.defenseplugin.lib;

import com.hypixel.hytale.builtin.path.PathPlugin;
import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.UUID;
import javax.annotation.Nonnull;

public final class ATPrefabPathHelper {
    private ATPrefabPathHelper() {
    }

    public static PatrolPathMarkerEntity addMarker(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> playerRef, @Nonnull UUID pathId, @Nonnull String pathName, double pauseTime, float obsvAngleDegrees, short targetIndex, int worldgenId) {
        World world = ((EntityStore)store.getExternalData()).getWorld();
        PatrolPathMarkerEntity waypoint = new PatrolPathMarkerEntity(world);
        waypoint.initialise(pathId, pathName, targetIndex, pauseTime, obsvAngleDegrees * ((float)Math.PI / 180F), worldgenId, store);
        TransformComponent transformComponent = (TransformComponent)store.getComponent(playerRef, TransformComponent.getComponentType());

        assert transformComponent != null;

        Vector3d playerPosition = transformComponent.getPosition().clone();
        Vector3f playerBodyRotation = transformComponent.getRotation().clone();
        PatrolPathMarkerEntity waypointEntity = (PatrolPathMarkerEntity)world.spawnEntity(waypoint, playerPosition, playerBodyRotation);
        if (waypointEntity != null) {
            Ref<EntityStore> waypointRef = waypointEntity.getReference();
            if (waypointRef != null && waypointRef.isValid()) {
                TransformComponent waypointTransformComponent = (TransformComponent)store.getComponent(waypointRef, TransformComponent.getComponentType());
                Vector3f waypointRotation = waypointTransformComponent.getRotation();
                waypointRotation.assign(playerBodyRotation);
                Model model = PathPlugin.get().getPathMarkerModel();
                store.putComponent(waypointRef, ModelComponent.getComponentType(), new ModelComponent(model));
                String displayName = PatrolPathMarkerEntity.generateDisplayName(worldgenId, waypointEntity);
                Message displayNameMessage = Message.raw(displayName);
                store.putComponent(waypointRef, DisplayNameComponent.getComponentType(), new DisplayNameComponent(displayNameMessage));
                store.putComponent(waypointRef, Nameplate.getComponentType(), new Nameplate(displayName));
            }
        }
        return waypointEntity;
    }
}
