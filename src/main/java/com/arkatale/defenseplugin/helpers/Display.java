package com.arkatale.defenseplugin.helpers;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.UUID;

public class Display {
    public void showTitle(UUID playerUUID, String text){

//        https://hytalemodding.dev/en/docs/guides/plugin/text-hologram#bonus-section---creating-title-holograms-with-code
//        UUID playerUUID = ctx.sender().getUuid();
        PlayerRef playerRef = Universe.get().getPlayer(playerUUID);
        World world = Universe.get().getWorld(playerRef.getWorldUuid());
        Transform playerTransform = playerRef.getTransform();
        world.execute(() -> {
            Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
            ProjectileComponent projectileComponent = new ProjectileComponent("Projectile");
            holder.putComponent(ProjectileComponent.getComponentType(), projectileComponent);
            holder.putComponent(TransformComponent.getComponentType(), new TransformComponent(playerTransform.getPosition().clone(), playerTransform.getRotation().clone()));
            holder.ensureComponent(UUIDComponent.getComponentType());
            if (projectileComponent.getProjectile() == null) {
                projectileComponent.initialize();
                if (projectileComponent.getProjectile() == null) {
                    return;
                }
            }
            holder.addComponent(NetworkId.getComponentType(), new NetworkId(world.getEntityStore().getStore().getExternalData().takeNextNetworkId()));
            holder.addComponent(Nameplate.getComponentType(), new Nameplate(text));
            world.getEntityStore().getStore().addEntity(holder, com.hypixel.hytale.component.AddReason.SPAWN);
        });
    }
}
