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
- Markdown: `docs/manual-tests/manual-tests-etap-1.1.md`
- CSV: `docs/manual-tests/manual-tests-etap-1.1.csv`

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

# ▶️ Jak uruchomić aplikację

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.owsiankagrzegorz.expensetracker.app.ExpenseTrackerApp"