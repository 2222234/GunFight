package com.rbc.gunfight.Command.Main.Reload;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class ClearUser extends CommandToDo {

    public ClearUser(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "clearuser";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            ModeMap map = modeManager.getMapManager().getMap(args[0]);
            if (map != null) {
                modeManager.getMapManager().clearUser(map);
                sender.sendMessage(
                        Config.langWithPrefix("clearuser").replace("{map}", args[0]));
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
        for (String name : modeManager.getMapManager().getNames()) {
            sn = sn.concat(sn.isEmpty() ? name : "|".concat(name));
        }
        sn = sn.concat("| ");
        return sn;
    }

    @Override
    public ArrayList<String> tab(CommandSender sender, String[] args) {
        ArrayList<String> thisCmds = new ArrayList<>();
        modeManager.getMapManager().getNames().forEach(name -> {
            if (name.contains(args[0])) {
                thisCmds.add(name);
            }
        });
        return thisCmds;
    }
}
