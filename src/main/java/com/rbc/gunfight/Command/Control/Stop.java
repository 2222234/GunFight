package com.rbc.gunfight.Command.Control;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.MapManager;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stop extends CommandToDo {
    private final MapManager mapManager;

    public Stop(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        mapManager = modeManager.getMapManager();
        name = "stop";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            ModeMap map = mapManager.getMap((Player) sender);
            if (map != null) {
                if (map.getModeController().stop()) {
                    sender.sendMessage(
                            Config.langWithPrefix("stop_success")
                                    .replace("{map}", map.getName()));
                } else {
                    sender.sendMessage(
                            Config.langWithPrefix("stop_fail")
                                    .replace("{map}", map.getName()));
                }
            } else {
                sender.sendMessage(Config.langWithPrefix("not_found_map"));
            }
            return;
        }
        sender.sendMessage(
                Config.langWithPrefix("right_command")
                        .replace("{command}", getCmd()
                                .concat(" ")
                                .concat(subNames(sender))));
    }
}
