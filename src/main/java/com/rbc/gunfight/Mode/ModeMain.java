package com.rbc.gunfight.Mode;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.GunFight;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public abstract class ModeMain extends JavaPlugin {
    protected GunFight gunFight;

    public abstract void reg();

    public void setPlugin(GunFight gunFight) {
        this.gunFight = gunFight;
    }

    public File getFolder() {
        File file = new File(gunFight.getDataFolder(), getModeName());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public abstract ArrayList<CommandToDo> getEditCmds();

    public abstract ArrayList<CommandToDo> getGameCmds();

    public abstract ArrayList<CommandToDo> getControlCmds();

    public abstract ArrayList<CommandToDo> getMainCmds();

    public abstract void regEvents(PluginManager pluginManager);

    public abstract ModeController getModeController();

    public abstract ModeMap createMap(String name);

    public abstract ModeMap loadMap(File file);

    public abstract boolean reload();

    public String getModeName() {
        return getDescription().getName();
    }

    public String getModeAuthor() {
        return getDescription().getAuthors().toString();
    }

    public String getModeVersion() {
        return getDescription().getVersion();
    }

    public boolean check(String name, String author, String version) {
        return getModeName().equals(name) && getModeAuthor().equals(author) && getModeVersion().equals(version);
    }

    public String getInfo() {
        return getModeName().concat("-").concat(getModeAuthor()).concat("-").concat(getModeVersion());
    }
}

