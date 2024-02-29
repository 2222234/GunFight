package com.rbc.gunfight.Command.Edit;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.MapManager;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMain;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Create extends CommandToDo {
    private final MapManager mapManager;

    public Create(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        mapManager = modeManager.getMapManager();
        name = "create";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            ModeMain mode = modeManager.getMode(args[0]);
            if (mode != null) {
                ModeMap map = mode.createMap(args[1]);
                if (mapManager.createMap(map)) {
                    map.save();
                    map.setUser((Player) sender);
                    sender.sendMessage(
                            Config.langWithPrefix("create")
                                    .replace("{map}", args[1])
                                    .replace("{mode}", args[0]));
                    return;
                }
                sender.sendMessage(
                        Config.langWithPrefix("duplicate_names")
                                .replace("{map}", args[0]));
            } else {
                sender.sendMessage(
                        Config.langWithPrefix("not_found_mode"));
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
        for (ModeMain mode : modeManager.getModes()) {
            sn = sn.concat(sn.isEmpty() ? mode.getModeName() : "|".concat(mode.getModeName()));
        }
        return sn;
    }

    @Override
    public ArrayList<String> tab(CommandSender sender, String[] args) {
        ArrayList<String> thisCmds = new ArrayList<>();
        modeManager.getNames().forEach(name -> {
            if (name.contains(args[0])) {
                thisCmds.add(name);
            }
        });
        return thisCmds;
    }
}
