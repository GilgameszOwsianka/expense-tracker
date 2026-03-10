package com.owsiankagrzegorz.expensetracker.app.command;

import com.owsiankagrzegorz.expensetracker.app.report.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportsMenuCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(ReportsMenuCommand.class);

    private final AppContext ctx;
    private final ReportRegistry registry;

    public ReportsMenuCommand(AppContext ctx) {
        this.ctx = ctx;
        this.registry = new ReportRegistry();

        // registration order = menu order
        this.registry.register(new MonthlySummaryReportStrategy());
        this.registry.register(new PeriodSummaryReportStrategy());
        this.registry.register(new CategoryBreakdownReportStrategy());
        this.registry.register(new TotalsSummaryReportStrategy());
    }

    @Override
    public void execute() {
        ctx.printer().info("\n--- Reports ---");
        registry.all().values().forEach(s -> ctx.printer().info(s.key() + ") " + s.label()));
        ctx.printer().info("Choose option: ");

        String option = ctx.input().readLineTrimmed();
        var strategy = registry.get(option);

        if (strategy == null) {
            log.warn("Unknown report option selected: option={}", option);
            ctx.printer().error("Unknown option.");
            return;
        }

        log.info("Selected report: key={}, label={}", strategy.key(), strategy.label());
        strategy.execute(ctx);
        log.info("Finished report: key={}", strategy.key());
    }
}