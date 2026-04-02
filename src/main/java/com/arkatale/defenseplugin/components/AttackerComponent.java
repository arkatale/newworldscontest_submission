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

    public static void setComponentType(ComponentType<EntityStore, AttackerComponent> componentType1) {
//        AttackerComponent.componentType = componentType;
        componentType = componentType1;

//        ^^fix - doch nicht
//        [2026/04/02 13:29:59 SEVERE]                         [Hytale] Exception in thread Thread[#98,WorldThread - default,5,InnocuousForkJoinWorkerThreadGroup]:
//java.lang.IllegalStateException: Invalid component at index 6 expected class com.arkatale.defenseplugin.components.AttackerComponent but found null
//	at com.hypixel.hytale.component.Archetype.validateComponents(Archetype.java:173)
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
