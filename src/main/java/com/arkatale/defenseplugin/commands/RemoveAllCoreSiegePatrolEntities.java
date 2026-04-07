package com.arkatale.defenseplugin.commands;

import com.arkatale.defenseplugin.DefensePlugin;
import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.spatial.SpatialResource;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolShowAnchor;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class RemoveAllCoreSiegePatrolEntities extends AbstractPlayerCommand {



    public RemoveAllCoreSiegePatrolEntities() {
        super("removeArkaTalePatrolPath", "Remove Core Attack Points in Radius 10");
        addAliases("removeCoreAttackPoints");
    }

    @Override
    protected void execute(@NonNull CommandContext commandContext, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        var packetHandler = playerRef.getPacketHandler();

        var pos = playerRef.getTransform().getPosition();
//        this.createAnchorEntityAt(pos, world);
//        packetHandler.writeNoCache(new BuilderToolShowAnchor(this.anchorEntityPosition.x, this.anchorEntityPosition.y, this.anchorEntityPosition.z));

//      \src\main\java\com\arkatale\defenseplugin\systems\DefendBlockSystem.java:83
        var entityStore = world.getEntityStore();
        SpatialResource<Ref<EntityStore>, EntityStore> spatial = entityStore.getStore().getResource(EntityModule.get().getEntitySpatialResourceType());
        List<Ref<EntityStore>> entities = SpatialResource.getThreadLocalReferenceList();
        spatial.getSpatialStructure().collect(pos, 10, entities);
        //        world.getEntityStore().getStore().removeentities
        ////        world.getEntityStore().getStore().
//        commandContext.senderAsPlayerRef().getStore().entities
        for(Ref<EntityStore> entityRef : entities) {

//            var has =   store.getComponent(entityRef, PatrolPathMarkerEntity.getComponentType());
//            Universe.get().sendMessage(Message.raw(""));
//            if(has != null){
            try{
                var comp = store.getComponent(entityRef, Nameplate.getComponentType());
//                var test = comp.getText();
                if(comp != null && comp.getText().contains("ArkaTale_CoreSiege")){
                    Universe.get().sendMessage(Message.raw("Trying to remove" + entityRef.toString()));
                    store.removeEntity(entityRef, RemoveReason.UNLOAD);
                }

            }catch (Exception e){
                HytaleLogger.getLogger().atSevere().log("Couldn't remove because " + e.getMessage());
            }

            }
//
//        }
    }
}
