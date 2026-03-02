# Stage 2.3 – Manual Test Checklist

## 1. Application startup

### 1.1 Start without seed
- Run application normally
- Select option 1 (List transactions)
- Expected: "No transactions yet."

### 1.2 Start with seed
- Run application with VM option: -Dseed=true
- Select option 1
- Expected: 3 demo transactions are displayed

---

## 2. Command Pattern

### 2.1 Exit command
- Select option 0
- Expected: Application exits cleanly

### 2.2 Unknown option
- Enter invalid number (e.g., 99)
- Expected: "Unknown option."

---

## 3. CRUD operations

### 3.1 Add transaction
- Select 2
- Provide valid amount, category, type
- Expected: Transaction added with incremented ID

### 3.2 Delete transaction
- Select 3
- Provide valid ID
- Expected: "Transaction removed."
- Provide invalid ID
- Expected: "Transaction not found."

---

## 4. Filter by type

- Select 4
- Enter WYDATEK or PRZYCHOD
- Expected: Only matching transactions are shown

---

## 5. CSV persistence

### 5.1 Save
- Select 5
- Expected: File created in data/transactions.csv

### 5.2 Load
- Select 6
- Expected: Transactions loaded successfully

---

## 6. Query

- Select 7
- Test optional filters (type, date range, amount range)
- Expected: Correct filtered result

---

## 7. Reports submenu (Strategy pattern)

### 7.1 Monthly summary
- Select 8 → 1
- Enter valid month
- Expected: Formatted totals displayed

### 7.2 Period summary
- Select 8 → 2
- Enter valid date range
- Expected: Correct totals

### 7.3 Category breakdown
- Select 8 → 3
- Enter date range
- Expected: Totals per category formatted to 2 decimal places

### 7.4 Unknown reports option
- Select 8 → invalid input
- Expected: "Unknown option."