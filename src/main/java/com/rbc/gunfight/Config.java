package com.rbc.gunfight;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Config {
    private static JavaPlugin plugin;
    private static FileConfiguration lang;
    private static FileConfiguration init;

    public static void setPlugin(JavaPlugin plugin) {
        Config.plugin = plugin;
    }

    public static void reload() {
        plugin.saveResource("init.yml", false);
        plugin.saveResource("lang.yml", false);
        init = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "init.yml"));
        lang = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "lang.yml"));
    }

    private static String lang(String key) {
        String s = lang.getString(key);
        if (s == null) {
            s = "undefined";
            lang.set(key, s);
            s = s.concat(" ").concat(key);
        }
        return s;
    }

    public static String langWithPrefix(String key) {
        String prefix = lang.getString("name");
        String txt = lang(key);
        return prefix + " " + txt;
    }

    public static String init(String key) {
        String s = init.getString(key);
        if (s == null) {
            s = "undefined";
            init.set(key, s);
            s = s.concat(" ").concat(key);
        }
        return s;
    }
}
