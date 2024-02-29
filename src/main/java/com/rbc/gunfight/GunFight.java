package com.rbc.gunfight;

import com.rbc.gunfight.Command.CommandReg;
import com.rbc.gunfight.Manager.MapManager;
import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunFight extends JavaPlugin {
    private ModeManager modeManager;

    public static GunFight getInstance() {
        return getPlugin(GunFight.class);
    }

    public ModeManager getModeManager() {
        return modeManager;
    }

    @Override
    public void onEnable() {
        Config.setPlugin(this);
        Config.reload();
        modeManager = new ModeManager();
        new CommandReg(modeManager);
        modeManager.reload(true);
    }

    @Override
    public void onDisable() {
        MapManager mapManager = modeManager.getMapManager();
        mapManager.stopAll();
        mapManager.saveAll();
    }
}
