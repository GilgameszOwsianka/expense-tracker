package com.owsiankagrzegorz.expensetracker.app.command;

public class ExitSignal {

    private boolean exitRequested = false;

    public void requestExit() {
        this.exitRequested = true;
    }

    public boolean isExitRequested() {
        return exitRequested;
    }
}