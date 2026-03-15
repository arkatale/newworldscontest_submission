package com.arkatale.towerplugin;

import com.arkatale.towerplugin.component.TowerComponent;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

public class TowerPlugin extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public TowerPlugin(JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from %s version %s", this.getName(), this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        var at_towerComponentType = this.getEntityStoreRegistry().registerComponent(TowerComponent.class, "AT_TowerComponent", TowerComponent.CODEC);

        TowerComponent.setComponentType(at_towerComponentType);
    }

    @Override
    protected void start() {

    }
}
