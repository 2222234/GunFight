package com.rbc.gunfight.Command.Main.Info;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ListMap extends CommandToDo {

    public ListMap(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "listmap";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(
                    Config.langWithPrefix("load_maps")
                            .replace("{maps}", modeManager.getMapManager().getNames().toString())
                            .replace("{counts}", String.valueOf(modeManager.getMapManager().getMaps().size())));
            return;
        }
        sender.sendMessage(
                Config.langWithPrefix("right_command")
                        .replace("{command}", getCmd()
                                .concat(" ")
                                .concat(subNames(sender))));
    }
}
