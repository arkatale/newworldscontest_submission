package com.arkatale.defenseplugin.systems;

import com.arkatale.defenseplugin.components.AttackerComponent;
import com.arkatale.defenseplugin.components.DefendBlockComponent;
import com.arkatale.defenseplugin.logic.DefendSession;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.spatial.SpatialResource;
import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.SetBlockSettings;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DefendBlockSystem extends EntityTickingSystem<ChunkStore> {

//    public static ArrayList<Float> keineAhnung;
    public static Map<Integer, Float> keineAhnung = new ConcurrentHashMap<>();;

//    public void DefendBlockSystem{
////        keineAhnung = new ArrayList<Float>();
//    }

    @Override
    public void tick(float v, int i, @NonNull ArchetypeChunk<ChunkStore> archetypeChunk, @NonNull Store<ChunkStore> store, @NonNull CommandBuffer<ChunkStore> commandBuffer) {

        var current = keineAhnung.get(i);
        //warum machst du das? weil es ist doch erstmal egal mit dem time zeug weil
        // falls das jmd nutzen wird - wird es eh wahrscheinlich nur 1 concurrent block

//        from DelayedEntitySystem
//        DelayedEntitySystem.Data<ECS_TYPE> data = (DelayedEntitySystem.Data)store.getResource(this.resourceType);
//        data.dt += dt;
//        if (data.dt >= this.intervalSec) {
//            float fullDt = data.dt;
//            data.dt = 0.0F;
//            super.tick(fullDt, systemIndex, store);
//        }
        if(current == null){
            current = 0f;
            keineAhnung.put(i, 0f);
        }

        var newValue = current + v;
        keineAhnung.replace(i, newValue);

        if(newValue >= 1f){
            stuff(v, i, archetypeChunk, store, commandBuffer);
            keineAhnung.replace(i, 0f);
        }
    }
        public void stuff(float v, int i, @NonNull ArchetypeChunk<ChunkStore> archetypeChunk, @NonNull Store<ChunkStore> store, @NonNull CommandBuffer<ChunkStore> commandBuffer) {

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
                    var posI = toDefendPosition;

            if(defendBlock.getCorruption() > 10){
//                        Universe.get().sendMessage(Message.raw("You loose"));
//
//                        world.execute(() -> {
                world.setBlock(posI.x, posI.y, posI.z, BlockType.EMPTY_KEY, SetBlockSettings.NO_SEND_PARTICLES);
                //^^ fixed by BlockType.Empty_Key instead of String.valueOf(BlockType.EMPTY)
                //[2026/04/03 17:29:36 SEVERE]         [World|default] Failed to run task!
                //java.lang.IllegalArgumentException: Unknown key! BlockType{id=Empty, unknown=false, group='Air', blockSoundSetId='EMPTY', blockSoundSetIndex=0, particles=null, blockParticleSetId='null', blockBreakingDecalId='null', particleColor=null, effect=null, textures=null, textureSideMask='null', cubeShadingMode=Standard, customModel='null', customModelTexture=null, customModelScale=1.0, customModelAnimation='null', drawType=Empty, material=Empty, opacity=Transparent, requiresAlphaBlending=false, tickProcedurenull, tintUp=null, tintDown=null, tintNorth=null, tintSouth=null, tintWest=null, tintEast=null, biomeTintUp=0, biomeTintDown=0, biomeTintNorth=0, biomeTintSouth=0, biomeTintWest=0, biomeTintEast=0, randomRotation=None, variantRotation=None, flipType=SYMMETRIC, rotationYawPlacementOffset=None, transitionTexture='null', transitionToGroups=null, hitboxType='Full', hitboxTypeIndex=0, interactionHitboxType='null', interactionHitboxTypeIndex=-2147483648, light=null, movementSettings=BlockMovementSettings{isClimbable=falseisBouncy=falsebounceSpeed=0.0, climbUpSpeedMultiplier=1.0, climbDownSpeedMultiplier=1.0, climbLateralSpeedMultiplier=1.0, drag=0.82, friction=0.18, terminalVelocityModifier=1.0, horizontalSpeedMultiplier=1.0, jumpForceMultiplier=1.0}, flags=com.hypixel.hytale.protocol.BlockFlags@9e5b, interactionHint='null', isTrigger=false, damageToEntities=0, allowsMultipleUsers=true, bench=null, gathering=null, placementSettings=null, state=null, ambientSoundEventId='null', ambientSoundEventIndex='0', conditionalSounds=null, interactionSoundEventId='null', interactionSoundEventIndex='0', isLooping=false, farming=null, supportDropType=BREAK, maxSupportDistance=0, support={}, supporting={}, interactions={}, railConfig=null}
                //	at com.hypixel.hytale.server.core.universe.world.accessor.BlockAccessor.setBlock(BlockAccessor.java:48)
                //	at com.hypixel.hytale.server.core.universe.world.accessor.IChunkAccessorSync.setBlock(IChunkAccessorSync.java:136)
                //	at com.arkatale.defenseplugin.systems.DefendBlockSystem.lambda$stuff$0(DefendBlockSystem.java:106)

//                        });
                return;
            }

                    for(var entity : entities){
//                        todo check if has AttackerComponent - at later time if that works then
//                        var store = entity.getStore();
//                        store.getComponent(entity, NPCComp)
                        NPCEntity npcComponent = entityStore.getStore().getComponent(entity, Objects.requireNonNull(NPCEntity.getComponentType()));
//                        fix by using store of entity store instead of chunkstore param Inferred type 'T' for type parameter 'T' is not within its bound; should implement 'com.hypixel.hytale.component.Component<com.hypixel.hytale.server.core.universe.world.storage.ChunkStore>'
                        if(npcComponent == null) continue;

                        var npcTypeId = npcComponent.getNPCTypeId();

                        if(! npcTypeId.startsWith("NexusAttacker_"))
                            return;

//                        Universe.get().sendMessage(Message.raw("corruption +1"));
                        defendBlock.setCorruption(defendBlock.getCorruption()+1);

//                        commandBuffer.removeEntity(entity, RemoveReason.REMOVE);
                        entityStore.getStore().removeEntity(entity, RemoveReason.REMOVE);
                    }

                });

        var test = "";
    }

    @Override
    public @Nullable Query<ChunkStore> getQuery() {
        return Query.and(DefendBlockComponent.getComponentType());
    }
}
