package com.rbc.gunfight.Command.Main.Info;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Manager.ModeManager;

public class Info extends CommandToDo {

    public Info(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "info";
        subCmds.add(new Main(this, modeManager));
        subCmds.add(new Mode(this, modeManager));
        subCmds.add(new ListMode(this, modeManager));
        subCmds.add(new ListMap(this, modeManager));
    }
}
