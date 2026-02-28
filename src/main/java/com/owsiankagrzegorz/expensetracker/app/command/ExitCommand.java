package com.owsiankagrzegorz.expensetracker.app.command;

public class ExitCommand implements Command {

    private final AppContext ctx;
    private final ExitSignal exit;

    public ExitCommand(AppContext ctx, ExitSignal exit) {
        this.ctx = ctx;
        this.exit = exit;
    }

    @Override
    public void execute() {
        ctx.printer().info("Bye!");
        exit.requestExit();
    }
}