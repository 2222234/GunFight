package com.rbc.gunfight.Manager;

import com.rbc.gunfight.Config;
import com.rbc.gunfight.GunFight;
import com.rbc.gunfight.Mode.ModeController;
import com.rbc.gunfight.Mode.ModeMain;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;

public class ModeManager {
    private final ArrayList<ModeMain> modes;

    private final EventsManager eventsManager;
    private final MapManager mapManager;

    public ModeManager() {
        modes = new ArrayList<>();
        eventsManager = new EventsManager(this);
        mapManager = new MapManager(this);
    }

    public EventsManager getEventsManager() {
        return eventsManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public ArrayList<ModeMain> getModes() {
        return modes;
    }

    public ModeMain getMode(String name) {
        for (ModeMain modeMain : modes) {
            if (modeMain.getModeName().equals(name)) {
                return modeMain;
            }
        }
        return null;
    }

    public File getModeFolder() {
        return new File(GunFight.getInstance().getDataFolder(), "Modes");
    }

    public boolean reload(boolean compulsion) {
        if (!compulsion) {
            for (ModeMap map : mapManager.getMaps()) {
                if (map.getModeController().getStage() != ModeController.STOP) {
                    return false;
                }
            }
        } else {
            mapManager.getMaps().forEach((ModeMap map) -> map.getModeController().stop());
        }
        modes.clear();
        for (ModeMain modeMain : loadModes()) {
            File folder = new File(GunFight.getInstance().getDataFolder(), modeMain.getModeName());
            if (!folder.exists() || !folder.isDirectory()) {
                folder.mkdir();
            }
            modes.add(modeMain);
            String s = Config.langWithPrefix("load_mode")
                    .replace("{mode}", modeMain.getModeName());
            sendMessage(s);
            Bukkit.getLogger().info(s);
        }
        eventsManager.reload();
        mapManager.reload();
        return true;
    }

    public boolean reload(String name, boolean compulsion) {
        ModeMain modeMain = getMode(name);
        if (modeMain == null) {
            return false;
        }
        if (!compulsion) {
            for (ModeMap map : mapManager.getMaps()) {
                if (!map.getModeMain().equals(modeMain)) {
                    continue;
                }
                if (map.getModeController().getStage() != ModeController.STOP) {
                    return false;
                }
            }
        } else {
            for (ModeMap map : mapManager.getMaps()) {
                if (map.getModeMain().equals(modeMain)) {
                    map.getModeController().stop();
                    mapManager.reload(map.getMapFile().getName(), compulsion);
                }
            }
        }
        eventsManager.reload(name);
        return modeMain.reload();
    }

    private void sendMessage(String s) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("gunfight.main")) {
                player.sendMessage(s);
            }
        }
    }

    public ModeMain getMode(Class modeMainClass) {
        for (ModeMain modeMain : modes) {
            if (modeMain.getClass().equals(modeMainClass)) {
                return modeMain;
            }
        }
        return null;
    }

    private ArrayList<ModeMain> loadModes() {
        ArrayList<ModeMain> modes = new ArrayList<>();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin instanceof ModeMain) {
                ModeMain modeMain = (ModeMain) plugin;
                modeMain.setPlugin(GunFight.getInstance());
                modeMain.reg();
                modes.add((ModeMain) plugin);
            }
        }
        return modes;
    }

    public ModeMap checkMode(File file) {
        FileConfiguration map = YamlConfiguration.loadConfiguration(file);
        for (ModeMain modeMain : modes) {
            if (modeMain.check(map.getString("mode.name"), map.getString("mode.author"), map.getString("mode.version"))) {
                ModeMap modeMap = modeMain.loadMap(file);
                modeMap.setName(map.getString("name"));
                String s = Config.langWithPrefix("load_map")
                        .replace("{map}", modeMap.getName())
                        .replace("{mode}", modeMain.getModeName());
                sendMessage(s);
                Bukkit.getLogger().info(s);
                return modeMap;
            }
        }
        return null;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        modes.forEach(modeMain -> names.add(modeMain.getModeName()));
        return names;
    }
}
