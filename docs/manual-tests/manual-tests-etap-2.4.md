# Stage 2.4 – Manual Test Checklist (BigDecimal + CSV path)

## 0. Preconditions
- Application builds successfully:
  - `mvn clean test`
  - `mvn exec:java -Dexec.mainClass="com.owsiankagrzegorz.expensetracker.app.ExpenseTrackerApp"`

---

## 1. Money input uses BigDecimal (no floating point errors)

### 1.1 Add transaction with decimal amount (dot)
- Select option: `2) Add transaction`
- Amount: `10.10`
- Category: `Test`
- Type: `WYDATEK`
- Expected:
  - Transaction is added successfully
  - Amount is displayed as `10.10` (2 decimal places)

### 1.2 Add transaction with more than 2 decimals (rounding)
- Select option: `2) Add transaction`
- Amount: `10.129`
- Category: `Test`
- Type: `WYDATEK`
- Expected:
  - Amount is rounded to `10.13` (HALF_UP) and displayed with 2 decimals

### 1.3 Money correctness: 0.10 + 0.20 = 0.30
- Add two transactions:
  - `0.10` (WYDATEK)
  - `0.20` (WYDATEK)
- Go to reports:
  - Monthly summary for current month (or Period summary for today)
- Expected:
  - Totals show exact `0.30` (not `0.300000...`)

---

## 2. Query / Filter (amount range is BigDecimal)

### 2.1 Query by amount range
- Select option: `7) Query / Filter transactions`
- Provide:
  - Min amount: `10.00`
  - Max amount: `20.00`
  - Leave other filters empty
- Expected:
  - Returned transactions have `amount >= 10.00` and `amount <= 20.00`

### 2.2 Invalid amount range
- Select option: `7) Query / Filter transactions`
- Provide:
  - Min amount: `20.00`
  - Max amount: `10.00`
- Expected:
  - Validation message shown
  - User can retry without crashing

---

## 3. Reports formatting (BigDecimal + 2 decimals)

### 3.1 Monthly summary
- Select: `8) Reports` → `1) Monthly summary`
- Enter valid month (e.g. current month)
- Expected:
  - Income / Expense / Balance shown
  - All monetary values formatted to 2 decimals

### 3.2 Period summary
- Select: `8) Reports` → `2) Period summary`
- Enter valid date range
- Expected:
  - Correct totals
  - All monetary values formatted to 2 decimals

### 3.3 Category breakdown
- Select: `8) Reports` → `3) Category breakdown`
- Enter valid date range
- Expected:
  - Totals per category shown
  - All monetary values formatted to 2 decimals

---

## 4. CSV persistence: custom filename/path (2.4-7)

### 4.1 Save with default filename
- Select option: `5) Save transactions to CSV`
- At prompt, press Enter (empty input)
- Expected:
  - File `transactions.csv` is created in the working directory

### 4.2 Save to custom filename (without extension)
- Select option: `5) Save transactions to CSV`
- Provide: `backup`
- Expected:
  - File `backup.csv` is created

### 4.3 Save to subdirectory path (directory auto-create)
- Select option: `5) Save transactions to CSV`
- Provide: `report/transaction1`
- Expected:
  - Directory `report/` is created if missing
  - File `report/transaction1.csv` is created

### 4.4 Load from custom path
- Select option: `6) Load transactions from CSV`
- Provide: `report/transaction1.csv`
- Expected:
  - Transactions are loaded successfully

### 4.5 Load missing file (no crash)
- Select option: `6) Load transactions from CSV`
- Provide: `does-not-exist.csv`
- Expected:
  - Clear error message (file not found)
  - Application continues running

---