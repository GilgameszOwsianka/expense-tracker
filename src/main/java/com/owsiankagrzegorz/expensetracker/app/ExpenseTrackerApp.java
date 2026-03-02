package com.owsiankagrzegorz.expensetracker.app;

import com.owsiankagrzegorz.expensetracker.app.command.*;
import com.owsiankagrzegorz.expensetracker.app.io.ConsolePrinter;
import com.owsiankagrzegorz.expensetracker.app.io.InputReader;
import com.owsiankagrzegorz.expensetracker.model.Transaction;
import com.owsiankagrzegorz.expensetracker.model.TransactionType;
import com.owsiankagrzegorz.expensetracker.persistence.CsvTransactionPersistence;
import com.owsiankagrzegorz.expensetracker.repository.InMemoryTransactionRepository;
import com.owsiankagrzegorz.expensetracker.service.ExpenseTrackerService;
import com.owsiankagrzegorz.expensetracker.service.query.*;
import com.owsiankagrzegorz.expensetracker.service.report.TransactionReportService;
import java.time.LocalDate;
import java.util.Scanner;

public class ExpenseTrackerApp {

    public static void main(String[] args) {

        var repository = new InMemoryTransactionRepository();
        var persistence = new CsvTransactionPersistence();
        var service = new ExpenseTrackerService(repository, persistence);
        var queryService = new TransactionQueryService(repository);
        var reportService = new TransactionReportService(repository);

        boolean seed = Boolean.getBoolean("seed");
        if (seed) {
            service.addTransaction(new Transaction(1L, 100.0, "Jedzenie", LocalDate.of(2026, 1, 1), TransactionType.WYDATEK));
            service.addTransaction(new Transaction(2L, 200.0, "Transport", LocalDate.of(2026, 1, 1), TransactionType.WYDATEK));
            service.addTransaction(new Transaction(3L, 300.0, "Przychód", LocalDate.of(2026, 1, 1), TransactionType.PRZYCHOD));
        }

        InputReader input = new InputReader(new Scanner(System.in));
        ConsolePrinter printer = new ConsolePrinter();

        AppContext ctx = new AppContext(service, queryService, input, printer, reportService);

        ExitSignal exitSignal = new ExitSignal();
        Command exitCommand = new ExitCommand(ctx, exitSignal);
        Command unknownCommand = new UnknownOptionCommand(ctx);

        CommandRegistry registry = new CommandRegistry(unknownCommand);
        registry.register(1, new ListTransactionsCommand(ctx));
        registry.register(2, new AddTransactionCommand(ctx));
        registry.register(3, new DeleteTransactionCommand(ctx));
        registry.register(4, new FilterByTypeCommand(ctx));
        registry.register(5, new SaveToCsvCommand(ctx));
        registry.register(6, new LoadFromCsvCommand(ctx));
        registry.register(7, new QueryTransactionsCommand(ctx));
        registry.register(8, new ReportsMenuCommand(ctx));
        registry.register(0, exitCommand);

        while (!exitSignal.isExitRequested()) {
            printer.printMainMenu();
            int choice = input.readMenuChoice();
            registry.get(choice).execute();
        }
        input.close();
    }
}