package com.rbc.gunfight.Command;

import com.rbc.gunfight.Command.Control.ControlCommand;
import com.rbc.gunfight.Command.Edit.EditCommand;
import com.rbc.gunfight.Command.Game.GameCommand;
import com.rbc.gunfight.Command.Main.MainCommand;
import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class CommandReg extends CommandToDo implements TabExecutor {
    public CommandReg(ModeManager modeManager) {
        super(null, modeManager);
        name = "/gunfight";
        subCmds.add(new EditCommand(this, modeManager));
        subCmds.add(new GameCommand(this, modeManager));
        subCmds.add(new ControlCommand(this, modeManager));
        subCmds.add(new MainCommand(this, modeManager));
        PluginCommand command = Bukkit.getPluginCommand("gunfight");
        if (command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        toDo(sender, command, label, args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return tab(sender, args);
    }
}
