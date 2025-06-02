# Intelligent Traffic Light Simulation

Symulacja inteligentnych świateł drogowych na skrzyżowaniu z adaptacyjnym sterowaniem.

## Uruchomienie

**Wymagania:** Java 11+, Maven 3.6+

```bash
# Test mode
mvn clean compile exec:java

# JSON mode  
mvn clean compile exec:java -Dexec.args="input.json output.json"

# Testy
mvn test
```

## Funkcjonalności

### Podstawowe:
- 4-kierunkowe skrzyżowanie (północ, południe, wschód, zachód)
- Adaptacyjne cykle świateł na podstawie natężenia ruchu
- Bezpieczeństwo ruchu (brak konfliktów)
- Obsługa JSON (addVehicle, step)

### Zaawansowane:
- Pojazdy awaryjne z natychmiastowym priorytetem
- Różne typy pojazdów (samochody, autobusy, karetki)
- Tryby ruchu (normalny, godziny szczytu, nocny, awaryjny)
- Inteligentny wybór faz na podstawie wagi ruchu

## Format JSON

**Wejście:**
```json
{
  "commands": [
    {"type": "addVehicle", "vehicleId": "car1", "startRoad": "north", "endRoad": "south"},
    {"type": "addVehicle", "vehicleId": "ambulance1", "startRoad": "west", "endRoad": "east", "vehicleType": "emergency"},
    {"type": "setTrafficMode", "trafficMode": "rush_hour"},
    {"type": "step"}
  ]
}
```

**Wyjście:**
```json
{
  "stepStatuses": [
    {"leftVehicles": ["car1", "ambulance1"]}
  ]
}
```

## Algorytm

System używa dwufazowego sterowania:
- **Fazy ruchu:** Północ-Południe i Wschód-Zachód
- **Adaptacyjny czas zielony:** 10-60 sekund na podstawie liczby oczekujących pojazdów
- **Priorytet awaryjny:** Natychmiastowe przełączenie dla pojazdów awaryjnych
- **System wag:** Pojazdy awaryjne (10x), autobusy (2x), samochody (1x)
- **Bezpieczeństwo:** Przejścia przez żółte i czerwone dla wszystkich kierunków przed zmianą fazy

Kontroler dynamicznie dostosowuje długość faz na podstawie natężenia ruchu i typów pojazdów oczekujących na każdym kierunku.
