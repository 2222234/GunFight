package com.rbc.gunfight.Command.Game;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class GameCommand extends CommandToDo {
    private final ArrayList<CommandToDo> baseCMDS;

    public GameCommand(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        baseCMDS = new ArrayList<>();
        name = "game";
    }

    public void reloadSubCmds() {
        subCmds.clear();
        subCmds.addAll(baseCMDS);
        modeManager.getModes().forEach(((ModeMain modemMain) -> subCmds.addAll(modemMain.getGameCmds())));
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("gunfight.game")) {
            reloadSubCmds();
            if (!subtoDo(sender, command, label, args)) {
                sender.sendMessage(getCmd().concat(" ").concat(subNames(sender)));
            }
        } else {
            sender.sendMessage(
                    Config.langWithPrefix("no_permission")
                            .replace("{permission}", "gunfight.game"));
        }
    }

    @Override
    public boolean subtoDo(CommandSender sender, Command command, String label, String[] args) {
        reloadSubCmds();
        return super.subtoDo(sender, command, label, args);
    }

    @Override
    public String subNames(CommandSender sender) {
        reloadSubCmds();
        return super.subNames(sender);
    }

    @Override
    public ArrayList<String> tab(CommandSender sender, String[] args) {
        reloadSubCmds();
        return super.tab(sender, args);
    }
}
