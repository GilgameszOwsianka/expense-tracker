package com.owsiankagrzegorz.expensetracker.app.command;

import com.owsiankagrzegorz.expensetracker.app.io.ConsolePrinter;
import com.owsiankagrzegorz.expensetracker.app.io.InputReader;
import com.owsiankagrzegorz.expensetracker.service.ExpenseTrackerService;
import com.owsiankagrzegorz.expensetracker.service.query.TransactionQueryService;
import com.owsiankagrzegorz.expensetracker.service.report.TransactionReportService;

public class AppContext {

    private final ExpenseTrackerService service;
    private final TransactionQueryService queryService;
    private final InputReader input;
    private final ConsolePrinter printer;
    private final TransactionReportService reportService;

    public AppContext(ExpenseTrackerService service, TransactionQueryService queryService, InputReader input, ConsolePrinter printer, TransactionReportService reportService) {
        this.service = service;
        this.queryService = queryService;
        this.input = input;
        this.printer = printer;
        this.reportService = reportService;
    }

    public ExpenseTrackerService service() { return service; }
    public TransactionQueryService queryService() { return queryService; }
    public InputReader input() { return input; }
    public ConsolePrinter printer() { return printer; }
    public TransactionReportService reportService() { return reportService; }
}
