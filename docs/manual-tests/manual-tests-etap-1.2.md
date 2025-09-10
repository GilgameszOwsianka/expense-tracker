# Testy manualne – Podetap 1.2

## Cel
Sprawdzenie poprawności działania tablicy transakcji w klasie `TransactionArrayManager`:
- dodawanie transakcji do tablicy,
- wyświetlanie wszystkich transakcji,
- filtrowanie po typie,
- obsługa pełnej tablicy.

## Kroki testowe

| Krok | Akcja                                                | Oczekiwany wynik                                                                 |
|------|------------------------------------------------------|----------------------------------------------------------------------------------|
| 1    | Uruchomić klasę `ExpenseTrackerApp`                 | Program uruchamia się bez błędów                                                 |
| 2    | Dodać kilka transakcji za pomocą `TransactionArrayManager` | Transakcje są dodane do tablicy                                                 |
| 3    | Wyświetlić wszystkie transakcje                     | Wyświetlone wszystkie dodane transakcje w konsoli                               |
| 4    | Wyświetlić transakcje filtrując po typie "WYDATEK" | Wyświetlone tylko transakcje typu "WYDATEK"                                     |
| 5    | Wyświetlić transakcje filtrując po typie "PRZYCHÓD"| Wyświetlone tylko transakcje typu "PRZYCHÓD"                                    |
| 6    | Spróbować dodać więcej transakcji niż pojemność tablicy | Dodatkowa transakcja nie zostaje dodana (metoda `addTransaction` zwraca false) |

## Uwagi
- Sprawdzić poprawność wyświetlanych wartości `amount`, `category`, `date` i `type`.
- Zweryfikować, czy metoda `filterByType` zwraca tylko poprawne transakcje.
- Sprawdzić reakcję programu przy próbie dodania większej liczby transakcji niż pojemność tablicy.
