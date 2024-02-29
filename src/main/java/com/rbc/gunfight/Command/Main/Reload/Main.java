package com.rbc.gunfight.Command.Main.Reload;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Main extends CommandToDo {

    public Main(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "main";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Config.reload();
            sender.sendMessage(
                    Config.langWithPrefix("reload_config"));
            return;
        }
        sender.sendMessage(
                Config.langWithPrefix("right_command")
                        .replace("{command}", getCmd()
                                .concat(" ")
                                .concat(subNames(sender))));
    }
}
