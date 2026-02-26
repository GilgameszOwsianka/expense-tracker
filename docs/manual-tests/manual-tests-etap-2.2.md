# Manual Tests – Etap 2.2
## Stream API: zapytania, filtrowanie, sortowanie i raporty

---

## Objective
Verify Stage 2.2 features:
- Query / filter transactions using TransactionQueryService (Stream API)
- Generate reports using TransactionReportService (Stream API aggregations)
- UI integration: new menu options for Queries and Reports
- Behavior unchanged for core CRUD and persistence

---

## 1) Query: filter by type
### Steps
1. Run app
2. Add at least:
    - one WYDATEK
    - one PRZYCHOD
3. Choose menu: Query / Filter
4. Type filter: WYDATEK (leave other fields empty)

### Expected
- Only WYDATEK transactions are displayed

---

## 2) Query: date range inclusive
### Steps
1. Ensure transactions exist with different dates
2. Query / Filter
3. Set dateFrom and dateTo so that at least 2 transactions are in range (inclusive)

### Expected
- Only transactions within the range are displayed
- Boundaries are inclusive

---

## 3) Query: sort + limit
### Steps
1. Add 3+ transactions with different amounts
2. Query / Filter
3. Sort by AMOUNT (ASC)
4. Limit = 2

### Expected
- Results are sorted correctly
- Only 2 results are printed

---

## 4) Report: monthly summary
### Steps
1. Ensure at least one income and one expense exist in the same month
2. Reports → Monthly summary
3. Provide month (yyyy-MM)

### Expected
- Income total, expense total and balance are calculated correctly for the month

---

## 5) Report: period summary
### Steps
1. Reports → Period summary
2. Provide from/to dates (yyyy-MM-dd)

### Expected
- Income total, expense total and balance are calculated correctly for the period

---

## 6) Report: category breakdown (optional type)
### Steps
1. Add transactions with at least 2 categories
2. Reports → Category breakdown
3. Provide from/to dates
4. Provide:
    - empty type (all), or
    - WYDATEK / PRZYCHOD

### Expected
- Totals grouped by category are displayed
- When type is provided, only that type is included
- Ordering is descending by total (most spent/earned first)

---

## Test Status
Stage 2.2 manual verification checklist prepared for execution.