package com.rbc.gunfight.Mode;

public abstract class ModeController {
    public static final int STOP = 0;
    protected int stage = STOP;

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public abstract boolean start();

    public abstract boolean stop();
}
