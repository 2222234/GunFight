package com.rbc.gunfight.Command.Edit;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EditCommand extends CommandToDo {
    private final ArrayList<CommandToDo> baseCMDS;

    public EditCommand(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "edit";
        baseCMDS = new ArrayList<>();
        baseCMDS.add(new Create(this, modeManager));
        baseCMDS.add(new Delete(this, modeManager));
        baseCMDS.add(new EditToggle(this, modeManager));
        baseCMDS.add(new Save(this, modeManager));
        baseCMDS.add(new UnUse(this, modeManager));
        baseCMDS.add(new Use(this, modeManager));
    }

    public void reloadSubCmds(Player player) {
        subCmds.clear();
        subCmds.addAll(baseCMDS);
        ModeMap map = modeManager.getMapManager().getMap(player);
        if (map != null) {
            subCmds.addAll(map.getModeMain().getEditCmds());
        }
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("gunfight.edit")) {
            reloadSubCmds((Player) sender);
            if (!subtoDo(sender, command, label, args)) {
                sender.sendMessage(getCmd().concat(" ").concat(subNames(sender)));
            }
        } else {
            sender.sendMessage(
                    Config.langWithPrefix("no_permission")
                            .replace("{permission}", "gunfight.edit"));
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
