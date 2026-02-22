# Manual Tests – Stage 1 (Regression Suite)
## Expense Tracker – Console Version (Stages 1.1 – 1.4)

---

## Objective

Full regression verification of Stage 1 functionality:

- Transaction creation and display
- Dynamic storage using ArrayList
- Console menu navigation and validation
- Add / Delete / Filter operations
- CSV persistence (Save / Load)
- Restart data consistency

---

# 1️⃣ Basic Transaction Display (Stage 1.1 Reference)
This verifies that the original Stage 1.1 behavior (transaction display) still works in the current console-based version.

## Purpose
Verify correct creation and display of a Transaction object.

### Steps
1. Run `ExpenseTrackerApp`
2. Add a valid transaction using menu option `2`
3. Use option `1` (List)

### Expected Result
Transaction is displayed correctly with:
- id
- amount
- category
- date
- type

Application runs without errors.

---

# 2️⃣ Core Operations (Stage 1.2 + 1.3)

## Application Startup

| Step | Action | Expected Result |
|------|--------|----------------|
| 1 | Run application | Menu displayed |
| 2 | Observe console | No crash |

---

## Menu Validation

| Input | Expected Result |
|--------|----------------|
| `abc` | Error message + re-prompt |
| `99` | "Unknown option. Try again." |
| `0` | Application terminates |

---

## Add Transaction (Option 2)

### Validation

| Input | Expected Result |
|--------|----------------|
| `abc` as amount | Error message |
| `-10` as amount | "Amount must be greater than 0." |
| Empty category | Error message |
| Invalid type | "Invalid type. Enter WYDATEK or PRZYCHOD." |
| Lowercase type | Accepted (converted to uppercase) |

### Positive Flow

1. Enter valid amount
2. Enter category
3. Enter type
4. ID generated automatically (max existing ID + 1)

Use option `1` to verify transaction appears.

---

## List Transactions (Option 1)

| Scenario | Expected Result |
|-----------|----------------|
| Transactions exist | All displayed |
| No transactions | "No transactions yet." |

---

## Delete Transaction (Option 3)

| Scenario | Expected Result |
|-----------|----------------|
| Non-numeric ID | Error message |
| Non-existing ID | "Transaction not found." |
| Existing ID | "Transaction removed." |
| Removing again | "Transaction not found." |

---

## Filter Transactions (Option 4)

| Scenario | Expected Result |
|-----------|----------------|
| Filter WYDATEK | Only expenses shown |
| Filter PRZYCHOD | Only income shown |
| No matches | "No transactions found for type: ..." |

---

# 3️⃣ CSV Persistence (Stage 1.4)

## Save to CSV (Option 5)

| Scenario | Expected Result |
|----------|----------------|
| Save with transactions | File created in `data/transactions.csv` |
| Save again | File overwritten successfully |

---

## Load from CSV (Option 6)

| Scenario | Expected Result |
|----------|----------------|
| Load existing file | Transactions loaded + count displayed |
| Load replaces memory | In-memory list replaced by file content |
| Missing file | "File not found" message |

---

# 4️⃣ Restart Persistence (End-to-End)

1. Add transaction (`2`)
2. Save (`5`)
3. Exit (`0`)
4. Restart application
5. Load (`6`)
6. List (`1`)

Expected Result:
Data restored correctly.

---

# Additional Observations

- ID increments correctly (max ID + 1)
- Date set to `LocalDate.now()`
- Application never crashes on invalid input
- Menu reappears after each operation
- Data directory created automatically if missing

---

## Test Status

Full regression suite executed manually.

Stage 1 functionality works correctly after completion of Stages 1.1–1.4.

---

# Stage 1 Status: COMPLETED