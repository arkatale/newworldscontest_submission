package com.arkatale.defenseplugin.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.Nullable;

public class DefendBlockComponent implements Component<ChunkStore> {
    public static final BuilderCodec<DefendBlockComponent> CODEC = BuilderCodec.builder(DefendBlockComponent.class, DefendBlockComponent::new)
            .append(new KeyedCodec<Integer>("Corruption", Codec.INTEGER),
                    (config, value) -> config.corruption = value, // Setter //why Cannot resolve symbol 'corruption'
                    (config) -> config.corruption).add() // Getter
            .build();

    private int corruption = 0;

    public DefendBlockComponent(DefendBlockComponent defendBlockComponent) {
        this.corruption = defendBlockComponent.getCorruption();
    }

    @Override
    public @Nullable Component<ChunkStore> clone() {
        return new DefendBlockComponent(this);
    }

    public int getCorruption() {
        return corruption;
    }

    private static ComponentType<ChunkStore,DefendBlockComponent> componentType;

    public static void setComponentType(ComponentType<ChunkStore,DefendBlockComponent> componentType) {
        DefendBlockComponent.componentType = componentType;
    }

    public static ComponentType<ChunkStore,DefendBlockComponent> getComponentType(){
        return componentType;
    }
}
