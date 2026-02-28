package com.owsiankagrzegorz.expensetracker.app.command;

public class UnknownOptionCommand implements Command {

    private final AppContext ctx;

    public UnknownOptionCommand(AppContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        ctx.printer().error("Unknown option.\nTry again.");
    }
}
