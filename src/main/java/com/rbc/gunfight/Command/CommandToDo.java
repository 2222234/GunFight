package com.rbc.gunfight.Command;

import com.rbc.gunfight.Manager.ModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommandToDo {
    protected String name;
    protected CommandToDo superCmd;
    protected ArrayList<CommandToDo> subCmds;
    protected ModeManager modeManager;

    public CommandToDo(CommandToDo superCmd, ModeManager modeManager) {
        this.superCmd = superCmd;
        this.modeManager = modeManager;
        subCmds = new ArrayList<>();
    }

    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (!subtoDo(sender, command, label, args)) {
            sender.sendMessage(getCmd().concat(" ").concat(subNames(sender)));
        }
    }

    public boolean subtoDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (CommandToDo commandToDo : subCmds) {
                if (commandToDo.name.equals(args[0])) {
                    commandToDo.toDo(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
                    return true;
                }
            }
        }
        return false;
    }

    public String subNames(CommandSender sender) {
        String sn = "";
        for (CommandToDo commandToDo : subCmds) {
            sn = sn.isEmpty() ? commandToDo.name : sn.concat("|").concat(commandToDo.name);
        }
        return sn;
    }

    public ArrayList<String> tab(CommandSender sender, String[] args) {
        ArrayList<String> thisCmds = new ArrayList<>();
        if (args.length > 1) {
            for (CommandToDo commandToDo : subCmds) {
                if (commandToDo.name.equals(args[0])) {
                    return commandToDo.tab(sender, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }
        for (CommandToDo commandToDo : subCmds) {
            if (args.length == 1 && !commandToDo.name.contains(args[0])) {
                continue;
            }
            thisCmds.add(commandToDo.name);
        }
        return thisCmds;
    }

    public String getCmd() {
        if (superCmd != null) {
            return superCmd.getCmd().concat(" " + name);
        }
        return name;
    }
}
