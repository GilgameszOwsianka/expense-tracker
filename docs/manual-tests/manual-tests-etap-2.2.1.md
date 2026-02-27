# Manual Tests – Etap 2.2.1
## Polishing / UX Hardening (Console)

---

## Objective
Verify Stage 2.2.1 improvements:
- robust console input validation (no crashes on invalid input)
- extended query options (category, amount range, sort direction, limit)
- improved report formatting (aligned columns, separators, consistent amounts)

---

## 1) Input validation – invalid dates
### Steps
1. Run app
2. Go to Query / Filter
3. When prompted for dates, enter invalid values:
    - `abc`
    - `2026/01/01`
    - `2026-99-99`
4. Provide a valid date after the error

### Expected
- App does not crash
- Clear message is displayed (expected format)
- User can retry until valid or press Enter for empty (optional fields)

---

## 2) Input validation – invalid month (Monthly report)
### Steps
1. Reports → Monthly summary
2. Enter invalid month values:
    - `abc`
    - `2026/01`
    - `2026-13`
3. Enter valid month `2026-01`

### Expected
- App does not crash
- Clear message is displayed (expected format `yyyy-MM`)
- User can retry

---

## 3) Query – amount range early validation
### Steps
1. Query / Filter
2. Enter:
    - Min amount: `200`
    - Max amount: `100`

### Expected
- App displays error immediately: minAmount must be <= maxAmount
- User is asked again for Min/Max (does not continue to next prompts)

---

## 4) Query – sort field/direction validation
### Steps
1. Query / Filter
2. Sort field: enter `DATA` (invalid)
3. Sort direction: enter `DOWN` (invalid)
4. Provide valid values:
    - Sort field: `AMOUNT`
    - Sort direction: `DESC`

### Expected
- App does not crash
- Invalid values show allowed options
- Valid values are accepted and applied

---

## 5) Query – limit validation
### Steps
1. Query / Filter
2. Limit results:
    - enter `abc` (invalid)
    - enter `-1` (invalid if builder enforces >0)
    - enter `2` (valid)

### Expected
- Invalid number triggers retry
- Invalid limit produces clear error (or retry, depending on implementation)
- Valid limit returns limited number of results

---

## 6) Reports – formatting
### Steps
1. Run Monthly summary, Period summary, Category breakdown
2. Observe console output

### Expected
- Separators are printed
- Columns are aligned
- Amounts are formatted consistently with 2 decimal places
- Category breakdown has header row and aligned totals

---

## Test Status
Stage 2.2.1 manual verification checklist prepared for execution.