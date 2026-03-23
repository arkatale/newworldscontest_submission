package com.arkatale.defenseplugin.events;

import com.arkatale.defenseplugin.DefensePlugin;
import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
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


        //world and entityStore missing/needed
        defensePlugin.startDefenseAt(placeholderPositionToDefend, world, entityStore);
        ;
        Universe.get().sendMessage(Message.raw("test"));
    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Archetype.empty();
    }
}
