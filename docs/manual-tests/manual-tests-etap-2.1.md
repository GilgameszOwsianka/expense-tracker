# Manual Tests – Etap 2.1
## Service + Repository + Persistence separation (Ports & Adapters)
---

## Objective
Verify architectural refactor:
- UI delegates use-cases to `ExpenseTrackerService`
- Runtime storage uses `TransactionRepository` (InMemory)
- Save/Load uses `TransactionPersistence` (CSV adapter)
- Application behavior unchanged from user perspective

---

## 1) Basic Flow (UI -> Service)
### Steps
1. Run application
2. Add transaction (Option 2):
    - amount: 100
    - category: Jedzenie
    - type: wydatek
3. List transactions (Option 1)

### Expected
- Transaction is added and visible on list
- ID is generated automatically (nextTransactionId via service)

---

## 2) Delete Transaction (Use-case)
### Steps
1. Add 2 transactions
2. Delete the second transaction by id (Option 3)
3. List (Option 1)

### Expected
- Only remaining transaction is displayed
- Correct success/failure message is printed

---

## 3) Filter by Type
### Steps
1. Add one WYDATEK and one PRZYCHOD
2. Filter WYDATEK (Option 4)
3. Filter PRZYCHOD (Option 4)

### Expected
- Filtering returns only correct types

---

## 4) Persistence (CSV) via Service
### Steps
1. Add 2 transactions
2. Save (Option 5)
3. Exit (Option 0)
4. Restart application
5. Load (Option 6)
6. List (Option 1)

### Expected
- Loaded transactions count matches saved
- Types remain correct (WYDATEK/PRZYCHOD)
- No direct CSV logic is required in UI (behavior unchanged)

---

## Test Status
Etap 2.1 manual verification completed after service/repository/persistence refactor.