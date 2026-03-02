package com.owsiankagrzegorz.expensetracker.app.report;

import com.owsiankagrzegorz.expensetracker.app.command.AppContext;

public interface ReportStrategy {
    String key();     // np. "1"
    String label();   // np. "Monthly summary"
    void execute(AppContext ctx);
}