package com.owsiankagrzegorz.expensetracker.app.command;

import com.owsiankagrzegorz.expensetracker.app.report.CategoryBreakdownReportStrategy;
import com.owsiankagrzegorz.expensetracker.app.report.MonthlySummaryReportStrategy;
import com.owsiankagrzegorz.expensetracker.app.report.PeriodSummaryReportStrategy;
import com.owsiankagrzegorz.expensetracker.app.report.ReportRegistry;

public class ReportsMenuCommand implements Command {

    private final AppContext ctx;
    private final ReportRegistry registry;

    public ReportsMenuCommand(AppContext ctx) {
        this.ctx = ctx;
        this.registry = new ReportRegistry();

        // registration order = menu order
        this.registry.register(new MonthlySummaryReportStrategy());
        this.registry.register(new PeriodSummaryReportStrategy());
        this.registry.register(new CategoryBreakdownReportStrategy());
    }

    @Override
    public void execute() {
        ctx.printer().info("\n--- Reports ---");
        registry.all().values().forEach(s ->
                ctx.printer().info(s.key() + ") " + s.label())
        );

        ctx.printer().info("Choose option: ");
        String option = ctx.input().readLineTrimmed();

        var strategy = registry.get(option);
        if (strategy == null) {
            ctx.printer().error("Unknown option.");
            return;
        }

        strategy.execute(ctx);
    }
}