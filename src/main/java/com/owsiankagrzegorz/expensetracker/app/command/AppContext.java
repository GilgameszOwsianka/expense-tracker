package com.owsiankagrzegorz.expensetracker.app.command;

import com.owsiankagrzegorz.expensetracker.app.io.ConsolePrinter;
import com.owsiankagrzegorz.expensetracker.app.io.InputReader;
import com.owsiankagrzegorz.expensetracker.service.ExpenseTrackerService;

public class AppContext {

    private final ExpenseTrackerService service;
    private final InputReader input;
    private final ConsolePrinter printer;

    public AppContext(ExpenseTrackerService service, InputReader input, ConsolePrinter printer) {
        this.service = service;
        this.input = input;
        this.printer = printer;
    }

    public ExpenseTrackerService service() { return service; }
    public InputReader input() { return input; }
    public ConsolePrinter printer() { return printer; }
}
