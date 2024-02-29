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

public class EditToggle extends CommandToDo {
    private final MapManager mapManager;

    public EditToggle(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        mapManager = modeManager.getMapManager();
        name = "editToggle";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            ModeMap map = mapManager.getMap((Player) sender);
            if (map != null) {
                map.setEdit(Boolean.parseBoolean(args[0]));
                sender.sendMessage(
                        Config.langWithPrefix("edit")
                                .replace("{toggle}", String.valueOf(Boolean.parseBoolean(args[0]))));
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

    @Override
    public String subNames(CommandSender sender) {
        return "true|false";
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
