# 💰 Expense Tracker

Projekt edukacyjny rozwijany etapowo w celu nauki:

- Java (OOP)
- Maven
- Git & GitHub PR workflow
- Testy jednostkowe (JUnit 5)
- Testy manualne (Markdown + CSV)

Docelowo projekt będzie rozwinięty do aplikacji webowej z użyciem:
Spring Boot, REST API, JPA, Hibernate oraz Docker.

---

# 🌿 Strategia gałęzi

- **`main`** – stabilna wersja projektu
- **feature/*`** – rozwój poszczególnych etapów
- PR workflow – każdy etap rozwijany na osobnej gałęzi i mergowany do `main`

---

# 🧩 Etap 1.1 – Pojedyncza transakcja (konsola)

## Cel
Podstawy OOP i testów jednostkowych.

## Klasy
- `Transaction`
- `ExpenseTrackerApp`

## Testy jednostkowe
- `TransactionTest`

## Testy manualne
- Markdown: [`docs/manual-tests/manual-tests-etap-1.1.md`](docs/manual-tests/manual-tests-etap-1.1.md)
- CSV: [`docs/manual-tests/manual-tests-etap-1.1.csv`](docs/manual-tests/manual-tests-etap-1.1.csv)

---

# 🧩 Etap 1.2 – Tablica transakcji (Array)

## Cel
Zarządzanie wieloma transakcjami w oparciu o tablicę.

## Klasy
- `TransactionArrayManager`
- Rozszerzona `ExpenseTrackerApp`

## Funkcjonalności
- Dodawanie transakcji
- Listowanie
- Filtrowanie po typie
- Obsługa pełnej tablicy

## Testy jednostkowe
- `TransactionArrayManagerTest`

## Testy manualne
- Markdown: [`docs/manual-tests/manual-tests-etap-1.2.md`](docs/manual-tests/manual-tests-etap-1.2.md)
- CSV: [`docs/manual-tests/manual-tests-etap-1.2.csv`](docs/manual-tests/manual-tests-etap-1.2.csv)

---

# 🧩 Etap 1.3 – ArrayList + Menu Konsolowe

## Cel
Przejście z tablicy na `ArrayList` oraz budowa profesjonalnego menu konsolowego.

## Klasy
- `TransactionListManager`
- Refaktoryzowana `ExpenseTrackerApp`

## Funkcjonalności
- Interaktywne menu (`Scanner + while + switch`)
- Listowanie transakcji
- Dodawanie transakcji (in-memory)
- Usuwanie transakcji po ID
- Filtrowanie po typie
- Walidacja danych wejściowych

## Testy jednostkowe
- `TransactionListManagerTest`

## Testy manualne
- Markdown: [`docs/manual-tests/manual-tests-etap-1.3.md`](docs/manual-tests/manual-tests-etap-1.3.md)
- CSV: [`docs/manual-tests/manual-tests-etap-1.3.csv`](docs/manual-tests/manual-tests-etap-1.3.csv)

---

# 🧩 Etap 1.4 – CSV File Persistence

## Cel
Wprowadzenie trwałości danych poprzez zapis i odczyt transakcji z pliku CSV.

## Nowe elementy
- `TransactionCsvRepository`
- Zapis do pliku `data/transactions.csv`
- Odczyt z pliku CSV
- Obsługa braku pliku
- Integracja z menu (opcje Save / Load)

## Testy jednostkowe
- `TransactionCsvRepositoryTest`

## Testy manualne
- Markdown: [`docs/manual-tests/manual-tests-etap-1.4.md`](docs/manual-tests/manual-tests-etap-1.4.md)
- CSV: [`docs/manual-tests/manual-tests-etap-1.4.csv`](docs/manual-tests/manual-tests-etap-1.4.csv)

---
## Stage 1 – Manual Testing

Stage 1 includes both incremental manual test documentation (1.1–1.4)
and a consolidated regression test suite.

### Incremental test documentation:
- 1.1 – Basic Transaction display
- 1.2 – Array-based manager
- 1.3 – Console menu + ArrayList
- 1.4 – CSV persistence

### Regression suite:
- Markdown: [`docs/manual-tests/manual-tests-etap-1.md`](docs/manual-tests/manual-tests-etap-1.md)
- CSV: [`docs/manual-tests/manual-tests-etap-1.csv`](docs/manual-tests/manual-tests-etap-1.csv)

The regression suite verifies full Stage 1 functionality after completing all substages.

---

# Etap 2.0 – Refactor typu transakcji (enum)
## Cel
Refactor pola `Transaction.type` z `String` na `TransactionType` enum w celu:
- eliminacji "magic strings"
- bezpieczeństwa typów (compile-time)
- prostszego filtrowania (porównanie enumów)
- stabilniejszego zapisu/odczytu CSV

## Zmiany
- `Transaction.type`: `String` -> `TransactionType`
- Dodano parser: `TransactionType.fromString(...)` (obsługa małych liter i `PRZYCHÓD`)
- CSV zapisuje typ jako `enum.name()` i wczytuje przez `fromString(...)`
- Menu konsolowe i filtrowanie działają na enumie

## Testy jednostkowe
- `TransactionListManagerTest` (zaktualizowany pod enum)

## Testy manualne
- Markdown: [`docs/manual-tests/manual-tests-etap-2.0.md`](docs/manual-tests/manual-tests-etap-2.0.md)
- CSV: [`docs/manual-tests/manual-tests-etap-2.0.csv`](docs/manual-tests/manual-tests-etap-2.0.csv)

---

# Etap 2.1 – Separacja warstw: Service / Repository / Persistence

## Cel
Wprowadzenie podstawowej architektury (Ports & Adapters):
- UI deleguje przypadki użycia do warstwy serwisu
- Dane w runtime przechowywane są przez `TransactionRepository` (InMemory)
- Zapis/odczyt realizuje `TransactionPersistence` (CSV adapter)

## Zmiany
- Dodano `TransactionRepository` + `InMemoryTransactionRepository`
- Dodano `ExpenseTrackerService` i przeniesiono do niego logikę aplikacyjną
- Dodano `TransactionPersistence` oraz `CsvTransactionPersistence`
- UI (`ExpenseTrackerApp`) nie wykonuje już logiki save/load bezpośrednio — deleguje do serwisu

## Testy
- Dodano testy serwisu + stub persistence (bez I/O)
- Manual tests:
- Markdown: [`docs/manual-tests/manual-tests-etap-2.1.md`](docs/manual-tests/manual-tests-etap-2.1.md)
- CSV: [`docs/manual-tests/manual-tests-etap-2.1.csv`](docs/manual-tests/manual-tests-etap-2.1.csv)

---

# Etap 2.2 – Stream API: zapytania i raporty

## Cel
Dodanie warstwy zapytań i raportów z użyciem Stream API:
- filtrowanie / sortowanie / limitowanie wyników (QueryService)
- podsumowania i agregacje (ReportService)

## Architektura
- `TransactionQueryService` operuje na `TransactionRepository` i realizuje pipeline Stream API
- `TransactionReportService` operuje na `TransactionRepository` i generuje DTO raportów
- UI zbiera parametry i deleguje logikę do serwisów

## Funkcje
- Query / Filter:
  - filtry: typ, zakres dat, kategoria, min/max kwota
  - sort: date/amount/category/type (ASC/DESC)
  - limit wyników
- Reports:
  - monthly summary
  - period summary
  - category breakdown (opcjonalny typ: WYDATEK/PRZYCHOD/all)

## Testy
- Unit tests: QueryService i ReportService
- Manual tests:
- Markdown: [`docs/manual-tests/manual-tests-etap-2.2.md`](docs/manual-tests/manual-tests-etap-2.2.md)
- CSV: [`docs/manual-tests/manual-tests-etap-2.2.csv`](docs/manual-tests/manual-tests-etap-2.2.csv)

---

# Etap 2.2.1 – Polishing / UX Hardening

## Cel
Ulepszenie stabilności i UX w konsoli bez zmian w logice biznesowej:
- walidacja inputu (brak crashy)
- rozbudowane opcje Query
- czytelniejsze raporty (formatowanie)

## Zmiany
- Bezpieczne parsowanie dat/miesięcy (retry + komunikaty)
- Query: kategoria, min/max kwota, sort direction, limit + walidacja
- Reports: wyrównane kolumny, separatory, spójny format kwot

## Testy
- Manual tests:
- Markdown: [`docs/manual-tests/manual-tests-etap-2.2.1.md`](docs/manual-tests/manual-tests-etap-2.2.1.md)
- CSV: [`docs/manual-tests/manual-tests-etap-2.2.1.csv`](docs/manual-tests/manual-tests-etap-2.2.1.csv)

---
# Workflow developerski

Projekt realizowany jest według uproszczonego, ale profesjonalnego procesu pracy dostosowanego do projektu jednoosobowego.

## Statusy w Project Board

Backlog → In Progress → Code Review → Testing/QA → Done

### Backlog
- Issue posiada opis, zakres oraz kryteria akceptacji.
- Jest przypisane do konkretnego Milestone.
- Jest gotowe do rozpoczęcia prac.

### In Progress
- Utworzony jest branch (najczęściej 1 branch na Milestone).
- Trwa implementacja.
- Powstają commity powiązane z numerem issue.

### Code Review
- Implementacja jest ukończona.
- Kod został sprawdzony (self-review).
- Issue oczekuje na otwarcie lub finalizację PR dla danego Milestone.

### Testing / QA
- PR dla Milestone jest otwarty.
- Wykonywana jest weryfikacja manualna (checklista testów).
- Uruchamiane są testy jednostkowe.
- Sprawdzana jest integracja wszystkich zmian.

### Done
- PR został zmergowany do branch `main`.
- Issue jest zamknięte.
- Zmiany są dostępne w stabilnej wersji projektu.

---

## Zasady

- Każde Issue przypisane jest do konkretnego Milestone.
- Najczęściej stosowany jest jeden branch na Milestone.
- Commity zawierają numer Issue w wiadomości commit.
- Statusy na Boardzie nie są cofane (odzwierciedlają rzeczywisty etap prac).
- Manual tests oraz aktualizacja README są częścią domknięcia Milestone.
---

# ▶️ Jak uruchomić aplikację

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.owsiankagrzegorz.expensetracker.app.ExpenseTrackerApp"