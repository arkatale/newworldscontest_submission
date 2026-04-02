package com.arkatale.defenseplugin.events;

import com.arkatale.defenseplugin.DefensePlugin;
import com.arkatale.defenseplugin.components.AttackerComponent;
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

        //Message.parse("test") Fehler, weil text und kein JSON ist
        //why hast du wrap in PARSE vorgeschlagen IntelliJ Idea?
        //[2026/03/23 19:07:05 SEVERE] [InteractionSystems$TickInteractionManagerSystem] Exception while ticking entity interactions! Removing!
        //java.io.IOException: Unexpected character: 74, 't' expected '{'!
        //	at com.hypixel.hytale.codec.util.RawJsonReader.expecting(RawJsonReader.java:922)
        //	at com.hypixel.hytale.codec.util.RawJsonReader.expect(RawJsonReader.java:357)
        //	at com.hypixel.hytale.codec.builder.BuilderCodec.decodeJson0(BuilderCodec.java:309)
        //	at com.hypixel.hytale.codec.builder.BuilderCodec.decodeJson(BuilderCodec.java:303)
        //	at com.hypixel.hytale.codec.function.FunctionCodec.decodeJson(FunctionCodec.java:52)
        //	at com.hypixel.hytale.server.core.Message.parse(Message.java:524)
        //	at com.arkatale.defenseplugin.events.WaveStartListener.handle(WaveStartListener.java:30)
        var placeholderPositionToDefend = pre.getTargetBlock();
        var player = archetypeChunk.getComponent(index, Player.getComponentType());
        World world = player.getWorld();
        EntityStore entityStore = player.getWorld().getEntityStore();
        var playerTransform = player.getTransformComponent();
        var position = playerTransform.getPosition();
        var rotation = Vector3f.lookAt(position);

        world.execute(() -> {
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
