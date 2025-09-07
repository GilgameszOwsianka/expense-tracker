# Expense Tracker

Projekt służący do nauki Javy i testów manualnych oraz automatyzujących.  
Rozwijany od konsolowej aplikacji po pełną aplikację webową z Spring Boot, Hibernate i frontendem.

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
- Plik **Markdown**: [`docs/manual-tests/manual-tests-etap-1.1.md`](docs/manual-tests/manual-tests-etap-1.1.md)
- Plik **CSV**: [`docs/manual-tests/manual-tests-etap-1.1.csv`](docs/manual-tests/manual-tests-etap-1.1.csv)

Opisują kroki manualne, oczekiwane wyniki i umożliwiają łatwe dokumentowanie testów konsolowych.

### Przykład uruchomienia
```bash
# Uruchomienie aplikacji w IntelliJ lub:
mvn compile exec:java -Dexec.mainClass="com.owsiankagrzegorz.expensetracker.app.ExpenseTrackerApp"
