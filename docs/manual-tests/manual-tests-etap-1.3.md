# Manual Tests – Stage 1.3
## Console Menu + ArrayList Implementation

---

## Objective

Verify correct behavior of the application after introducing:

- Console menu (`Scanner + while + switch`)
- Dynamic storage using `ArrayList`
- Adding transactions (in-memory)
- Deleting transactions by ID
- Filtering transactions by type
- Basic input validation

---

## Application Startup

| Step | Action | Expected Result |
|------|--------|----------------|
| 1 | Run `ExpenseTrackerApp` | Application starts without errors |
| 2 | Observe console | Menu is displayed correctly |

---

## Menu Validation

| Test Case | Input | Expected Result |
|------------|--------|----------------|
| Invalid input | `abc` | Error message and re-prompt |
| Invalid option | `99` | "Unknown option. Try again." |
| Exit | `0` | Application terminates |

---

## List Transactions (Option 1)

| Scenario | Expected Result |
|-----------|----------------|
| List when transactions exist | All transactions displayed |
| List when empty | "No transactions yet." |

---

## Add Transaction (Option 2)

### Validation Cases

| Input | Expected Result |
|--------|----------------|
| `abc` as amount | Error message |
| `-10` as amount | "Amount must be greater than 0." |
| Empty category | Error message |
| Invalid type | "Invalid type. Enter WYDATEK or PRZYCHOD." |
| Lowercase type | Accepted (converted to uppercase) |

### Positive Flow

1. Enter valid amount
2. Enter valid category
3. Enter valid type
4. System generates ID
5. Confirmation message displayed

After adding:
- Use option `1`
- New transaction should be visible

---

## Delete Transaction (Option 3)

| Scenario | Expected Result |
|-----------|----------------|
| Non-numeric ID | Error message |
| Non-existing ID | "Transaction not found." |
| Existing ID | "Transaction removed." |
| Removing same ID again | "Transaction not found." |

---

## Filter Transactions (Option 4)

| Scenario | Expected Result |
|-----------|----------------|
| Filter `WYDATEK` | Only expense transactions displayed |
| Filter `PRZYCHOD` | Only income transactions displayed |
| No matching transactions | "No transactions found for type: ..." |

---

## Additional Observations

- ID increments correctly (max ID + 1).
- Date is set to `LocalDate.now()`.
- After deletion, transaction is permanently removed from list.
- Application does not crash on invalid input.
- Menu reappears after each operation.

---

## Test Status

All scenarios executed manually.  
Application behaves as expected for Stage 1.3.

---

# Stage 1.3 Status: COMPLETED