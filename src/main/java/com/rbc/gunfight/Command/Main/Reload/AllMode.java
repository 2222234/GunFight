package com.rbc.gunfight.Command.Main.Reload;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class AllMode extends CommandToDo {

    public AllMode(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "allmode";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(
                    Config.langWithPrefix("reload_mode"));
            if (modeManager.reload(Boolean.parseBoolean(args[0]))) {
                sender.sendMessage(
                        Config.langWithPrefix("load_modes").replace("{modes}", modeManager.getNames().toString()));
                return;
            } else {
                sender.sendMessage(
                        Config.langWithPrefix("reload_mode_fail"));
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
        return "true/false";
    }

    @Override
    public ArrayList<String> tab(CommandSender sender, String[] args) {
        ArrayList<String> thisCmds = new ArrayList<>();
        if ("true".contains(args[0])) {
            thisCmds.add("true");
        }
        if ("false".contains(args[0])) {
            thisCmds.add("false");
        }
        return thisCmds;
    }
}
