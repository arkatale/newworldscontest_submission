package com.arkatale.towerplugin.systems;

import com.google.crypto.tink.subtle.Random;
import com.hypixel.hytale.component.*;
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

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TowerTickingSystem extends EntityTickingSystem<EntityStore> {
    private final ResourceType<EntityStore, TowerTickingSystem.Data<EntityStore>> resourceType = this.registerResource(TowerTickingSystem.Data.class, TowerTickingSystem.Data::new);
    private float intervalSec;
    private ArrayList<Vector3i> lastJumpBlockForIndex = new ArrayList<Vector3i>();

    public TowerTickingSystem(float intervalSec) {
        this.intervalSec = intervalSec;
    }

    public void setIntervalSec(float newInterval) {
        this.intervalSec = newInterval;
    }

    private static class Data<EntityStore> implements Resource<com.hypixel.hytale.server.core.universe.world.storage.EntityStore> {
        private float dt;

        @Nonnull
        public Resource<com.hypixel.hytale.server.core.universe.world.storage.EntityStore> clone() {
            TowerTickingSystem.Data<com.hypixel.hytale.server.core.universe.world.storage.EntityStore> data = new TowerTickingSystem.Data<com.hypixel.hytale.server.core.universe.world.storage.EntityStore>();
            data.dt = this.dt;
            return data;
        }
    }

    @Override
    public void tick(float dt, int index, @NonNull ArchetypeChunk<EntityStore> archetypeChunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer) {
        TowerTickingSystem.Data<EntityStore> data = (TowerTickingSystem.Data)store.getResource(this.resourceType);
        data.dt += dt;
        if (data.dt <= this.intervalSec) return;
            float fullDt = data.dt;
            data.dt = 0.0F;
//            super.tick(fullDt, systemIndex, store);


        var player = archetypeChunk.getComponent(index, Player.getComponentType());
        var playerTransform = store.getComponent(player.getReference(), TransformComponent.getComponentType());
        var playerPosition = playerTransform.getPosition().toVector3i();
        var world = player.getWorld();

        world.execute(
                () -> {
//                    lastJumpBlockForIndex[1] == null //c#

                    Vector3i lastPos;
//                    if(lastJumpBlockForIndex.size() -1 >= index){

//                    if(index +  1  <  lastJumpBlockForIndex.size()  ){
                    if(lastJumpBlockForIndex.size()    <  index +  1  ){
                        lastJumpBlockForIndex.add(playerPosition);
                    }

                    lastPos = lastJumpBlockForIndex.get(index);



                    lastPos = testBlockInRadiusForAir(world, lastPos, 5);
                    lastJumpBlockForIndex.set(index, lastPos);
//                    lastJumpBlockForIndex.q
                }
        );

    }

    private Vector3i testBlockInRadiusForAir(World world, Vector3i playerPosition, int radius) {
        var random = Random.randInt(radius);
        var dirRandom = Random.randInt(4);
        var direction = randomDir(dirRandom);
        var pos = playerPosition.clone().add(direction);

        var blockAtPos = world.getBlockType(pos);
        var blockTypeKey = getRandomMushRoomShelf();
        if(blockAtPos == BlockType.EMPTY){
            world.setBlock((int) pos.x, (int) pos.y, (int) pos.z, blockTypeKey);
        }
        return pos;
    }

    private String getRandomMushRoomShelf() {
        var list = new String[] {"Plant_Crop_Mushroom_Shelve_Brown", "Plant_Crop_Mushroom_Shelve_Green", "Plant_Crop_Mushroom_Shelve_Yellow"};
        var rand = Random.randInt(2);
        return list[rand];
    }

    private Vector3i randomDir(int random) {
        Vector3i dir = new Vector3i(0,0,0);
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
                break;
        }
        dir.add(Vector3i.UP);
        return dir;
    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Query.and(Player.getComponentType() );
    }
}
