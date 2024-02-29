package com.rbc.gunfight.Mode;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public abstract class ModeMap {
    protected String user;
    protected File mapFile;
    protected String name;
    protected ModeMain modeMain;
    protected ModeController modeController;
    protected Location secLoc1;
    protected Location secLoc2;
    protected Location gameLoc1;
    protected Location gameLoc2;
    protected World gameWorld;
    protected boolean edit;

    public ModeMap(ModeMain modeMain, File mapFile, String name) {
        this.modeMain = modeMain;
        this.mapFile = mapFile;
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(Player user) {
        this.user = user.getName();
    }

    public void setUserName(String user) {
        this.user = user;
    }

    public boolean isUser(Player user) {
        return this.user != null && this.user.equals(user.getName());
    }

    public File getMapFile() {
        return mapFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModeMain getModeMain() {
        return modeMain;
    }

    public ModeController getModeController() {
        return modeController;
    }

    public abstract boolean startCheck();

    public Location getSecLoc1() {
        return secLoc1;
    }

    public void setSecLoc1(Location secLoc1) {
        this.secLoc1 = secLoc1;
    }

    public Location getSecLoc2() {
        return secLoc2;
    }

    public void setSecLoc2(Location secLoc2) {
        this.secLoc2 = secLoc2;
    }

    public Location getGameLoc1() {
        return gameLoc1;
    }

    public void setGameLoc1(Location gameLoc1) {
        this.gameLoc1 = gameLoc1;
    }

    public Location getGameLoc2() {
        return gameLoc2;
    }

    public void setGameLoc2(Location gameLoc2) {
        this.gameLoc2 = gameLoc2;
    }

    public World getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(World gameWorld) {
        this.gameWorld = gameWorld;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean save() {
        FileConfiguration save = YamlConfiguration.loadConfiguration(mapFile);
        save.set("name", name);
        save.set("mode.name", modeMain.getModeName());
        save.set("mode.author", modeMain.getModeAuthor());
        save.set("mode.version", modeMain.getModeVersion());
        try {
            save.save(mapFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return saveMap();
    }

    public abstract boolean saveMap();
}
