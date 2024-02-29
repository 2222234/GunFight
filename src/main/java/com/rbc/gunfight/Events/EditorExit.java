package com.rbc.gunfight.Events;

import com.rbc.gunfight.Manager.MapManager;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EditorExit implements Listener {
    private final MapManager mapManager;

    public EditorExit(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    @EventHandler
    public void sl(PlayerQuitEvent pqe) {
        if (pqe.getPlayer().hasPermission("gunfight.edit")) {
            ModeMap map = mapManager.getMap(pqe.getPlayer());
            if (map != null) {
                map.setUserName(null);
                map.setEdit(!map.isEdit());
                map.save();
            }
        }
    }
}
