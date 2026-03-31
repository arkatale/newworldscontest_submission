package com.arkatale.defenseplugin.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.Nullable;

public class AttackerComponent implements Component<EntityStore> {
    public static final BuilderCodec<AttackerComponent> CODEC =
            BuilderCodec.builder(AttackerComponent.class, AttackerComponent::new)
                    .build();

    public static void setComponentType(ComponentType<EntityStore, AttackerComponent> componentType) {
        AttackerComponent.componentType = componentType;
    }

    //    daten? braucht der attacker irgendwas? außer core pos
    @Override
    public @Nullable Component<EntityStore> clone() {
        return null;
    }

    private static ComponentType<EntityStore,AttackerComponent> componentType;
    public static ComponentType<EntityStore,AttackerComponent> getComponentType(){
        return componentType;
    }

}
