package com.rbc.gunfight.Command.Edit;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.MapManager;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Use extends CommandToDo {
    private final MapManager mapManager;

    public Use(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        mapManager = modeManager.getMapManager();
        name = "use";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        ModeMap map = mapManager.getMap((Player) sender);
        if (map != null) {
            map.setUserName(null);
            sender.sendMessage(
                    Config.langWithPrefix("unuse")
                            .replace("{map}", map.getName()));
        }
        if (args.length == 1) {
            map = mapManager.getMap(args[0]);
            if (map != null) {
                if (map.getUser() == null) {
                    map.setUser(((Player) sender));
                    sender.sendMessage(
                            Config.langWithPrefix("use")
                                    .replace("{map}", args[0]));
                } else {
                    sender.sendMessage(
                            Config.langWithPrefix("map_alreadyuse")
                                    .replace("{player}", map.getUser()));
                }
            } else {
                sender.sendMessage(
                        Config.langWithPrefix("not_found_map_use"));
            }
            return;
        }
        sender.sendMessage(
                Config.langWithPrefix("right_command")
                        .replace("{command}", getCmd()
                                .concat(" ")
                                .concat(subNames(sender))));
    }

    @Override
    public String subNames(CommandSender sender) {
        String sn = "";
        for (String name : mapManager.getNames()) {
            sn = sn.concat(sn.isEmpty() ? name : "|".concat(name));
        }
        sn = sn.concat("| ");
        return sn;
    }

    @Override
    public ArrayList<String> tab(CommandSender sender, String[] args) {
        ArrayList<String> thisCmds = new ArrayList<>();
        mapManager.getNames().forEach(name -> {
            if (name.contains(args[0])) {
                thisCmds.add(name);
            }
        });
        return thisCmds;
    }
}
