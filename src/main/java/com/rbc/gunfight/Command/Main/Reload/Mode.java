package com.rbc.gunfight.Command.Main.Reload;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class Mode extends CommandToDo {

    public Mode(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "mode";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (modeManager.reload(args[0], Boolean.parseBoolean(args[1]))) {
                sender.sendMessage(
                        Config.langWithPrefix("load_mode")
                                .replace("{mode}", args[0]));
                return;
            } else {
                sender.sendMessage(
                        Config.langWithPrefix("load_mode_fail")
                                .replace("{mode}", args[0]));
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
        modeManager.getNames().forEach(name -> {
            if (name.contains(args[0])) {
                thisCmds.add(name);
            }
        });
        return thisCmds;
    }
}
