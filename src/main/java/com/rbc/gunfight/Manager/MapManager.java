package com.rbc.gunfight.Manager;

import com.rbc.gunfight.Config;
import com.rbc.gunfight.GunFight;
import com.rbc.gunfight.Mode.ModeController;
import com.rbc.gunfight.Mode.ModeMain;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class MapManager {
    private final ModeManager modeManager;
    private final ArrayList<ModeMap> maps;

    public MapManager(ModeManager modeManager) {
        this.modeManager = modeManager;
        maps = new ArrayList<>();
        reload();
    }

    public void saveAll() {
        maps.forEach(ModeMap::save);
    }

    public void stopAll() {
        maps.forEach(map -> map.getModeController().stop());
    }

    public File getMapFolder() {
        File mapFolder = new File(GunFight.getInstance().getDataFolder(), "Maps");
        if (!mapFolder.exists()) {
            mapFolder.mkdirs();
        }
        return mapFolder;
    }

    public void reload() {
        maps.clear();
        File mapFolder = new File(GunFight.getInstance().getDataFolder(), "Maps");
        if (!mapFolder.exists()) {
            mapFolder.mkdirs();
        }
        for (String s : mapFolder.list()) {
            ModeMap map = modeManager.checkMode(new File(mapFolder, s));
            if (map != null) {
                maps.add(map);
            }
        }
    }

    public boolean reload(String name, boolean compulsion) {
        File mapFolder = new File(GunFight.getInstance().getDataFolder(), "Maps");
        if (!mapFolder.exists()) {
            mapFolder.mkdirs();
        }
        ModeMap folderMap = modeManager.checkMode(new File(mapFolder, name));
        if (getFileNames().contains(name)) {
            ModeMap readyMap = getFileMap(name);
            if (!compulsion && readyMap.getModeController().getStage() != ModeController.STOP) {
                return false;
            }
            readyMap.getModeController().stop();
            maps.set(maps.indexOf(readyMap), folderMap);
        }
        return true;
    }

    public boolean createMap(ModeMap map) {
        if (!getFileNames().contains(map.getMapFile().getName())) {
            maps.add(map);
            return true;
        }
        return false;
    }

    public ArrayList<String> getFileNames() {
        ArrayList<String> names = new ArrayList<>();
        maps.forEach(map -> names.add(map.getMapFile().getName()));
        return names;
    }

    public void deleteMap(ModeMap map) {
        if (Boolean.parseBoolean(Config.init("delete_to_trash"))) {
            File trash = new File(GunFight.getInstance().getDataFolder(), "trash");
            if (!trash.exists() || !trash.isDirectory()) {
                trash.mkdir();
            }
            map.getMapFile().renameTo(new File(trash, map.getName()));
        } else {
            map.getMapFile().delete();
        }
    }

    public ArrayList<ModeMap> getMaps() {
        return maps;
    }

    public ArrayList<ModeMap> getMaps(ModeMain modeMain) {
        ArrayList<ModeMap> modeMaps = new ArrayList<>();
        for (ModeMap map : maps) {
            if (map.getModeMain().equals(modeMain)) {
                modeMaps.add(map);
            }
        }
        return modeMaps;
    }

    public ModeMap getMap(Player player) {
        for (ModeMap map : maps) {
            if (map == null) {
                continue;
            }
            if (map.isUser(player)) return map;
        }
        return null;
    }

    public ModeMap getMap(String mapName) {
        for (ModeMap map : maps) {
            if (map.getName().equals(mapName)) return map;
        }
        return null;
    }

    public ModeMap getFileMap(String fileName) {
        for (ModeMap map : maps) {
            if (map.getMapFile().getName().equals(fileName)) return map;
        }
        return null;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        maps.forEach(map -> names.add(map.getName()));
        return names;
    }

    public void clearUser(ModeMap map) {
        map.setUserName(null);
    }

    public boolean checkGames() {
        int games = 0;
        for (ModeMap map : maps) {
            if (map.getModeController().getStage() != ModeController.STOP) {
                games++;
            }
        }
        return games < Integer.parseInt(Config.init("game_max"));
    }
}
