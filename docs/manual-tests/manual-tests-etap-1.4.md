# Manual Tests – Stage 1.4
## CSV File Persistence (Save / Load)

---

## Objective

Verify correct behavior of CSV persistence:

- Save transactions to `data/transactions.csv`
- Load transactions from `data/transactions.csv`
- Data consistency after restart
- Basic error handling (missing file)

---

## File Location

Expected file path:

- `data/transactions.csv`

---

## Save to CSV (Option 5)

| Test Case | Preconditions | Steps | Expected Result |
|----------|---------------|-------|-----------------|
| Save with data | At least 1 transaction exists | Choose `5` | File is created and success message displayed |
| Save overwrites | CSV file already exists | Choose `5` again | File is overwritten (no crash) |

---

## Load from CSV (Option 6)

| Test Case | Preconditions | Steps | Expected Result |
|----------|---------------|-------|-----------------|
| Load existing file | `data/transactions.csv` exists | Choose `6` | Transactions loaded, message shows count |
| Load then list | File exists | Choose `6`, then `1` | Loaded transactions visible in list |
| Load replaces in-memory | Add a transaction, then load | Add via `2`, then `6`, then `1` | In-memory list replaced by file contents |

---

## Error Handling

| Test Case | Preconditions | Steps | Expected Result |
|----------|---------------|-------|-----------------|
| Load missing file | Delete `data/transactions.csv` | Choose `6` | "File not found" message, app continues |

---

## Restart Persistence Check

1. Add a transaction (`2`)
2. Save (`5`)
3. Exit (`0`)
4. Restart app
5. Load (`6`)
6. List (`1`)

✅ Expected: data restored after restart.

---

## ✅ Test Status

All scenarios executed manually.  
CSV save/load works as expected for Stage 1.4.