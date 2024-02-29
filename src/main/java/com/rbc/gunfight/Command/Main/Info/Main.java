package com.rbc.gunfight.Command.Main.Info;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.GunFight;
import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class Main extends CommandToDo {

    public Main(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "main";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            PluginDescriptionFile description = GunFight.getInstance().getDescription();
            sender.sendMessage(
                    Config.langWithPrefix("description")
                            .replace("{name}", description.getFullName())
                            .replace("{author}", description.getAuthors().toString()));
            return;
        }
        sender.sendMessage(
                Config.langWithPrefix("right_command")
                        .replace("{command}", getCmd()
                                .concat(" ")
                                .concat(subNames(sender))));
    }
}
