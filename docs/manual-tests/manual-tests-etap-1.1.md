# Testy manualne – Podetap 1.1

## Cel
Sprawdzenie poprawności utworzenia i wyświetlenia pojedynczej transakcji w konsoli.

## Kroki testowe
| Krok | Akcja                                                | Oczekiwany wynik                                                                                                                   |
|------|------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| 1    | Uruchomić klasę `ExpenseTrackerApp`                  | Program uruchamia się bez błędów                                                                                                   |
| 2    | Sprawdzić wyświetlenie obiektu Transaction w konsoli | W konsoli powinien pojawić się tekst podobny do: `Transaction{amount=150.0, category='Jedzenie', date=2025-09-07, type='WYDATEK'}` |

## Uwagi
- Sprawdzić poprawność wyświetlanych wartości amount, category, date i type.
