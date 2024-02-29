package com.rbc.gunfight.Command.Edit;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.MapManager;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Delete extends CommandToDo {
    private final MapManager mapManager;

    public Delete(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        mapManager = modeManager.getMapManager();
        name = "delete";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            ModeMap map = mapManager.getMap((Player) sender);
            if (map != null) {
                mapManager.deleteMap(map);
                sender.sendMessage(
                        Config.langWithPrefix("delete")
                                .replace("{map}", map.getName()));
            } else {
                sender.sendMessage(
                        Config.langWithPrefix("not_found_map"));
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
