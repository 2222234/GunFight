package com.rbc.gunfight.Manager;

import com.rbc.gunfight.Events.SelectLoc;
import com.rbc.gunfight.GunFight;
import com.rbc.gunfight.Mode.ModeMain;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EventsManager {
    private final ModeManager modeManager;

    public EventsManager(ModeManager modeManager) {
        this.modeManager = modeManager;
    }

    public void reload() {
        JavaPlugin plugin = GunFight.getPlugin(GunFight.class);
        HandlerList.unregisterAll(plugin);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SelectLoc(modeManager.getMapManager()), plugin);
        modeManager.getModes().forEach((ModeMain modeMain) -> {
            if (modeMain.isEnabled()) {
                HandlerList.unregisterAll(modeMain);
                modeMain.regEvents(pluginManager);
            }
        });
    }

    public void reload(String name) {
        ModeMain modeMain = modeManager.getMode(name);
        if (modeMain.isEnabled()) {
            HandlerList.unregisterAll(modeMain);
            PluginManager pluginManager = Bukkit.getPluginManager();
            modeMain.regEvents(pluginManager);
        }
    }
}
