package com.arkatale.towerplugin.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.Nullable;

public class TowerComponent implements Component<EntityStore> {
    public static final BuilderCodec<TowerComponent> CODEC =
            BuilderCodec.builder(TowerComponent.class, TowerComponent::new)
                    .append(new KeyedCodec<>("Score", Codec.INTEGER),
                            (data, value) -> data.score = value, // setter
                            data -> data.score) // getter
                    .addValidator(Validators.nonNull())
                    .add()
            .build();

    private int score = 0;

    private static ComponentType<EntityStore,TowerComponent> componentType;

    public static void setComponentType(ComponentType<EntityStore,TowerComponent> componentType) {
        TowerComponent.componentType = componentType;
    }

    public static ComponentType<EntityStore,TowerComponent> getComponentType(){
        return componentType;
    }
    public TowerComponent() {}

    public TowerComponent(TowerComponent towerComponent) {
        this.setScore(towerComponent.getScore());
    }

    @Override
    public @Nullable Component<EntityStore> clone() {
        return new TowerComponent(this);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
