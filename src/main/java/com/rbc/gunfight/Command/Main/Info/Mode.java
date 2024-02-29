package com.rbc.gunfight.Command.Main.Info;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Config;
import com.rbc.gunfight.Manager.ModeManager;
import com.rbc.gunfight.Mode.ModeMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class Mode extends CommandToDo {

    public Mode(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "mode";
    }

    @Override
    public void toDo(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            ModeMain modeMain = modeManager.getMode(args[0]);
            if (modeMain != null) {
                sender.sendMessage(
                        Config.langWithPrefix("mode_info")
                                .replace("{info}", modeMain.getInfo()));
                return;
            }
        }
        sender.sendMessage(
                Config.langWithPrefix("right_command")
                        .replace("{command}", getCmd()
                                .concat(" ")
                                .concat(subNames(sender))));
    }

    @Override
    public String subNames(CommandSender sender) {
        String sn = "";
        for (ModeMain modeMain : modeManager.getModes()) {
            sn = sn.isEmpty() ? modeMain.getModeName() : sn.concat("|").concat(modeMain.getModeName());
        }
        sn = sn.concat("| ");
        return sn;
    }

    @Override
    public ArrayList<String> tab(CommandSender sender, String[] args) {
        ArrayList<String> thisCmds = new ArrayList<>();
        modeManager.getModes().forEach(mode -> {
            if (mode.getModeName().equals(args[0])) {
                thisCmds.add(mode.getModeName());
            }
        });
        return thisCmds;
    }
}
