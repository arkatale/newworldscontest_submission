package com.arkatale.defenseplugin.events;

import com.arkatale.defenseplugin.DefensePlugin;
import com.arkatale.defenseplugin.components.AttackerComponent;
import com.arkatale.defenseplugin.components.DefendBlockComponent;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.arkatale.defenseplugin.logic.WaveManager;
import com.hypixel.hytale.server.npc.NPCPlugin;
import it.unimi.dsi.fastutil.Pair;
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
    public void handle(int index, @NonNull ArchetypeChunk<EntityStore> archetypeChunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer, UseBlockEvent.Pre pre) {
//        var defendBlock = archetypeChunk.getComponent(index, DefendBlockComponent.getComponentType());
//
//        if(defendBlock == null)
//            return;

        var placeholderPositionToDefend = pre.getTargetBlock();
        var player = archetypeChunk.getComponent(index, Player.getComponentType());
        World world = player.getWorld();
        EntityStore entityStore = player.getWorld().getEntityStore();
        var playerTransform = player.getTransformComponent();
        var position = playerTransform.getPosition();
        var rotation = Vector3f.lookAt(position);

        world.execute(() -> {
            var blockToCheck = world.getBlockType(placeholderPositionToDefend);

            if(!  blockToCheck.getId().startsWith("AT_Defend_Core")){
                return;
            }

            Pair<Ref<EntityStore>, INonPlayerCharacter> result;
            result = NPCPlugin.get().spawnNPC(store, "Crocodile", null, position, rotation);

            if(result != null){

                var npcRef = result.first();
//                var attackerComponent = store.getComponent(npcRef, AttackerComponent.getComponentType());
//
//                if (attackerComponent == null){
//                    store.addComponent(npcRef, AttackerComponent.getComponentType());
//                }
//                fix below by commenting above out
//                [2026/04/02 13:16:33 SEVERE]                [Hytale] Exception in thread Thread[#100,WorldThread - default,5,InnocuousForkJoinWorkerThreadGroup]:
//                    java.lang.IllegalStateException: Invalid component at index 6 expected class com.arkatale.defenseplugin.components.AttackerComponent but found null

            defensePlugin.startDefenseAt(placeholderPositionToDefend, world, entityStore, result, player);
            }

        });
        ;
        Universe.get().sendMessage(Message.raw("test"));


    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Archetype.empty();
    }
}
