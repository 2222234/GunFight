package com.rbc.gunfight.Command.Main.Reload;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class Map extends CommandToDo {

    public Map(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "map";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (modeManager.getMapManager().reload(args[0], Boolean.parseBoolean(args[1]))) {
                sender.sendMessage(
                        Config.langWithPrefix("reload_map").replace("{map}", args[0]));
                return;
            } else {
                sender.sendMessage(
                        Config.langWithPrefix("reload_map_fail").replace("{map}", args[0]));
            }
        }
        sender.sendMessage(
                Config.langWithPrefix("right_command")
                        .replace("{command}", getCmd()
                                .concat(" ")
                                .concat(subNames(sender))));
    }

    @Override
    public String subNames(CommandSender sender) {
        return "_ true/false";
    }

    @Override
    public ArrayList<String> tab(CommandSender sender, String[] args) {
        ArrayList<String> thisCmds = new ArrayList<>();
        modeManager.getMapManager().getFileNames().forEach(name -> {
            if (name.contains(args[0])) {
                thisCmds.add(name);
            }
        });
        return thisCmds;
    }
}
