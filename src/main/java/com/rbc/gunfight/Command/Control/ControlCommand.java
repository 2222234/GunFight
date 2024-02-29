package com.rbc.gunfight.Command.Control;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ControlCommand extends CommandToDo {
    private final ArrayList<CommandToDo> baseCMDS;

    public ControlCommand(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "control";
        baseCMDS = new ArrayList<>();
        baseCMDS.add(new Start(this, modeManager));
        baseCMDS.add(new Stop(this, modeManager));
    }

    public void reloadSubCmds(Player player) {
        subCmds.clear();
        subCmds.addAll(baseCMDS);
        ModeMap map = modeManager.getMapManager().getMap(player);
        if (map != null) {
            subCmds.addAll(map.getModeMain().getControlCmds());
        }
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("gunfight.control")) {
            reloadSubCmds((Player) sender);
            if (!subtoDo(sender, command, label, args)) {
                sender.sendMessage(getCmd().concat(" ").concat(subNames(sender)));
            }
        } else {
            sender.sendMessage(
                    Config.langWithPrefix("no_permission")
                            .replace("{permission}", "gunfight.control"));
        }
    }

    @Override
    public boolean subtoDo(CommandSender sender, Command command, String label, String[] args) {
        reloadSubCmds((Player) sender);
        return super.subtoDo(sender, command, label, args);
    }

    @Override
    public String subNames(CommandSender sender) {
        reloadSubCmds((Player) sender);
        return super.subNames(sender);
    }

    @Override
    public ArrayList<String> tab(CommandSender sender, String[] args) {
        reloadSubCmds((Player) sender);
        return super.tab(sender, args);
    }
}
