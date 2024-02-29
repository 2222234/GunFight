package com.rbc.gunfight.Events;

import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.MapManager;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectLoc implements Listener {
    private final MapManager mapManager;

    public SelectLoc(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    @EventHandler
    public void sl(PlayerInteractEvent pie) {
        if (pie.getPlayer().hasPermission("gunfight.edit")) {
            ModeMap map = mapManager.getMap(pie.getPlayer());
            if (map != null) {
                if (map.isEdit()) {
                    if (pie.getMaterial().equals(Material.STICK)) {
                        if (pie.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                            pie.getPlayer().sendMessage(
                                    Config.langWithPrefix("select_loc1")
                                            .replace("{loc}", pie
                                                    .getClickedBlock()
                                                    .getLocation()
                                                    .toVector()
                                                    .toString()));
                            map.setSecLoc1(pie.getClickedBlock().getLocation());
                        }
                        if (pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                            pie.getPlayer().sendMessage(
                                    Config.langWithPrefix("select_loc2")
                                            .replace("{loc}", pie
                                                    .getClickedBlock()
                                                    .getLocation()
                                                    .toVector()
                                                    .toString()));
                            map.setSecLoc2(pie.getClickedBlock().getLocation());
                        }
                        pie.setCancelled(true);
                    }
                }
            }
        }
    }
}
