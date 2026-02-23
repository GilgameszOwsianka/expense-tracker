# Manual Tests – Stage 2.0
## Refactor: Transaction.type String -> TransactionType enum
---

## Objective
Verify correct behavior after refactoring transaction type from `String` to `TransactionType` enum:
- Console input is parsed into enum (`WYDATEK` / `PRZYCHOD`)
- Input validation still works (invalid values rejected)
- Case-insensitive input works (lowercase accepted)
- Polish diacritics handled (`PRZYCHÓD` accepted)
- CSV persistence correctly saves/loads enum values
- Filtering uses enum values and returns correct results

---

## 1) Input Parsing (Console)
### Positive cases
| Test Case | Input | Expected Result |
|----------|-------|-----------------|
| Lowercase expense | `wydatek` | Accepted, transaction type set to `WYDATEK` |
| Uppercase expense | `WYDATEK` | Accepted |
| Lowercase income | `przychod` | Accepted, transaction type set to `PRZYCHOD` |
| Polish diacritics | `przychód` | Accepted, transaction type set to `PRZYCHOD` |

### Negative cases
| Test Case | Input | Expected Result |
|----------|-------|-----------------|
| Invalid type | `ABC` | Error: "Invalid type. Enter WYDATEK or PRZYCHOD." and re-prompt |
| Empty input | (empty) | Error + re-prompt |

---

## 2) Filter by Type (Option 4)
### Steps
1. Add at least 2 transactions:
    - one `WYDATEK`
    - one `PRZYCHOD`
2. Choose option `4`
3. Enter `WYDATEK`
4. Repeat option `4` and enter `PRZYCHOD`

### Expected Result
- Filter `WYDATEK` shows only expense transactions
- Filter `PRZYCHOD` shows only income transactions
- If no matches: "No transactions found for type: ..."

---

## 3) CSV Persistence (Save / Load)
### Save (Option 5)
| Test Case | Preconditions | Steps | Expected Result |
|----------|---------------|-------|-----------------|
| Save enum type | At least 1 transaction exists | Choose `5` | CSV created/overwritten successfully |

### Load (Option 6)
| Test Case | Preconditions | Steps | Expected Result |
|----------|---------------|-------|-----------------|
| Load enum type | CSV exists | Choose `6`, then `1` | Transactions loaded with correct types (`WYDATEK/PRZYCHOD`) |

---

## 4) End-to-End (Restart)
1. Add transaction type using `przychód` (with Polish character)
2. Save (`5`)
3. Exit (`0`)
4. Restart application
5. Load (`6`)
6. List (`1`)

✅ Expected: transaction restored correctly and type displayed as `PRZYCHOD`.

---

## Test Status
Stage 2.0 manual verification completed after enum refactor.