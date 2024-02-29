package com.rbc.gunfight.Command.Main.Reload;

import com.rbc.gunfight.Command.CommandToDo;
import com.rbc.gunfight.Manager.ModeManager;

public class Reload extends CommandToDo {

    public Reload(CommandToDo superCmd, ModeManager modeManager) {
        super(superCmd, modeManager);
        name = "reload";
        subCmds.add(new AllMode(this, modeManager));
        subCmds.add(new ClearUser(this, modeManager));
        subCmds.add(new Main(this, modeManager));
        subCmds.add(new Map(this, modeManager));
        subCmds.add(new Mode(this, modeManager));
    }
}
