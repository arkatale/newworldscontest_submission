package com.arkatale.towerplugin.systems;

import com.arkatale.towerplugin.component.TowerComponent;
import com.google.crypto.tink.subtle.Random;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class TowerTickingSystem extends EntityTickingSystem<EntityStore> {
    @Override
    public void tick(float v, int i, @NonNull ArchetypeChunk<EntityStore> archetypeChunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer) {
        var player = archetypeChunk.getComponent(i, Player.getComponentType());
        var playerTransform = store.getComponent(player.getReference(), TransformComponent.getComponentType());
        var playerPosition = playerTransform.getPosition();
        var world = player.getWorld();

        world.execute(
                () -> {
                    testBlockInRadiusForAir(world, playerPosition, 5);
                }
        );
    }

    private void testBlockInRadiusForAir(World world, Vector3d playerPosition, int radius) {
        var random = Random.randInt(radius);
        var dirRandom = Random.randInt(4);
        var direction = randomDir(dirRandom);
        var pos = playerPosition.add(direction);

        var blockAtPos = world.getBlockType(pos.toVector3i());
        if(blockAtPos != BlockType.EMPTY){
            world.setBlock((int) pos.x, (int) pos.y, (int) pos.z, "Plant_Crop_Mana2");
        }
    }

    private Vector3i randomDir(int random) {
        Vector3i dir;
        switch (random) {
            case 0:
                dir = Vector3i.NORTH;
                break;
            case 1:
                dir = Vector3i.EAST;
                break;
            case 2:
                dir = Vector3i.WEST;
                break;
            default:
                dir = Vector3i.SOUTH;
        }
        dir.add(Vector3i.UP);
        return dir;
    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Query.and(Player.getComponentType(), TowerComponent.getComponentType() );
    }
}
