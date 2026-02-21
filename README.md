# Expense Tracker

Projekt służący do nauki Javy i testów manualnych oraz automatyzujących.  
Rozwijany od konsolowej aplikacji po pełną aplikację webową z Spring Boot, Hibernate i frontendem.

## Role gałęzi

- **`main`** – zawiera aktualny stan projektu, gotowy do uruchomienia.
- **`feature/console-single-transaction`** – historia zmian dla etapu 1.1 (pojedyncza transakcja).
- **`feature/console-array-transactions`** – historia zmian dla etapu 1.2 (tablica transakcji).
- **`setup`** – początkowa konfiguracja projektu Maven, Git, struktura katalogów.

## Etap 1.1 – Pojedyncza transakcja (konsola)

### Cel
Pokazanie podstaw Javy, enkapsulacji, tworzenia obiektów i testów jednostkowych.

### Klasy
- **`Transaction`** – reprezentuje pojedynczą transakcję:
    - amount (kwota)
    - category (kategoria)
    - date (data, typ `LocalDate`)
    - type (WYDATEK / PRZYCHÓD)

- **`ExpenseTrackerApp`** – klasa uruchamiająca aplikację konsolową i wyświetlająca pojedynczą transakcję.

### Testy jednostkowe
- **`TransactionTest`** – sprawdza poprawne utworzenie obiektu Transaction (JUnit 5).

### Testy manualne
- Plik **Markdown**: [`docs/manual-tests/manual-tests-etap-1.2.md`](docs/manual-tests/manual-tests-etap-1.2)
- Plik **CSV**: [`docs/manual-tests/manual-tests-etap-1.2.csv`](docs/manual-tests/manual-tests-etap-1.2.csv)

Opisują kroki manualne, oczekiwane wyniki i umożliwiają łatwe dokumentowanie testów konsolowych.

## Etap 1.2 – Tablica transakcji (konsola)

### Cel

Sprawdzenie poprawności działania tablicy transakcji w klasie TransactionArrayManager:

- dodawanie transakcji do tablicy,
- wyświetlanie wszystkich transakcji,
- filtrowanie po typie,
- obsługa pełnej tablicy.

### Klasy

- **`TransactionArrayManager`** – zarządza tablicą transakcji, dodaje, listuje i filtruje.
- **`ExpenseTrackerApp`** – rozszerzona o wywołania tablicy transakcji i filtrowanie.

### Testy jednostkowe

- **`TransactionArrayManagerTest`** – testy JUnit 5:
- dodawanie transakcji,
- listowanie wszystkich transakcji,
- filtrowanie po typie,
- obsługa pełnej tablicy.

### Testy manualne
- Plik **Markdown**: [`docs/manual-tests/manual-tests-etap-1.2.md`](docs/manual-tests/manual-tests-etap-1.2)
- Plik **CSV**: [`docs/manual-tests/manual-tests-etap-1.2.csv`](docs/manual-tests/manual-tests-etap-1.2.csv)

Opisują kroki manualne, oczekiwane wyniki i umożliwiają łatwe dokumentowanie testów konsolowych.

### Przykład uruchomienia
```bash
# Uruchomienie aplikacji w IntelliJ lub:
mvn compile exec:java -Dexec.mainClass="com.owsiankagrzegorz.expensetracker.app.ExpenseTrackerApp"

