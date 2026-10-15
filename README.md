# SwiftParcel

A small courier/logistics domain built with plain Java and Maven. It models parcels and
consignments, the rules that govern loading them onto drivers' vehicles, delivery attempts,
and persistence of consignments to a SQLite database.

## Requirements

- JDK 21 or newer (the project targets Java 21)
- No Maven installation is required — use the bundled Maven Wrapper (`mvnw`)

## Getting started

Compile the project:

```bash
./mvnw compile
```

Run the test suite:

```bash
./mvnw test
```

Run the demo entry point (`za.co.swiftparcel.courier.Main`):

```bash
./mvnw exec:java -Dexec.mainClass=za.co.swiftparcel.courier.Main
```

The demo prints whether a consignment can be loaded, decides the next step for it, then
creates the SQLite schema, saves the consignment and its attempt, and reloads it.

To build the jar:

```bash
./mvnw package
```

## Project structure

```
src/main/java/za/co/swiftparcel/courier/
├── Database.java                 # SQLite connection helper
├── DatabaseSchema.java           # Creates the customers/parcels/delivery_attempts tables
├── Main.java                     # Demo entry point
├── dispatch/                     # Parcel hierarchy and dispatch hub
│   ├── Parcel.java               # Abstract base: tracking, sender, recipient, weight, delivered
│   ├── StandardParcel.java
│   ├── ExpressParcel.java
│   ├── FragileParcel.java
│   ├── Insurable.java            # Declared-value insurance contract
│   └── DispatchCentre.java       # Holds parcels and reports on them
├── model/                        # Persistence-oriented domain model
│   ├── AttemptOutcome.java       # DELIVERED, NO_ONE_HOME, WRONG_ADDRESS, REFUSED
│   ├── Consignment.java
│   ├── DeliveryAttempt.java
│   ├── Driver.java
│   └── ServiceLevel.java         # SAME_DAY, EXPRESS, STANDARD
├── repository/
│   └── ConsignmentRepository.java
└── service/
    └── DeliveryRulesService.java
```

## Domain overview

### Parcels (`dispatch`)

`Parcel` is abstract and holds the state every parcel shares. Concrete types implement:

- `deliveryFee()` — `BASE_FEE + (weightKg * FEE_PER_KG)`
- `deliveryDays()`
- `serviceLevel()` — short label used in `toString()`

| Type | Base fee | Fee/kg | Days | Service level | Insurance |
| --- | --- | --- | --- | --- | --- |
| `StandardParcel` | 5.0 | 2.5 | 5 | Standard | no |
| `ExpressParcel` | 15.0 | 5.0 | 1 | Express | yes |
| `FragileParcel` | 10.0 | 4.0 | 3 | Fragile | yes |

`Insurable` exposes `getDeclaredValue()` and `insurancePremium()` (2% of declared value).
`ExpressParcel` and `FragileParcel` are insurable; `FragileParcel` also carries handling notes.

`DispatchCentre` holds parcels, rejects duplicate tracking numbers, and reports
`totalDeliveryRevenue()`, `insurableParcels()`, and `totalInsurancePremiums()`.

### Consignments, drivers, and rules (`model`, `service`)

- `Consignment` — tracking number, weight, service level, zone, and flags (fragile,
  cash-on-delivery, address verified, on hold) plus a list of `DeliveryAttempt`s.
- `Driver` — id, name, zone, vehicle capacity, current load, shift and certification flags.
- `DeliveryRulesService` — `canLoadOntoVehicle`, `load`, and `decideNextStep` (max 3 attempts)
  encode the business rules for dispatching and re-delivery.

## Persistence

`DatabaseSchema` creates three tables: `customers`, `parcels`, and `delivery_attempts`.
`ConsignmentRepository` maps `Consignment`/`DeliveryAttempt` onto `parcels`/`delivery_attempts`
(save/upsert, `recordAttempt`, `findByTrackingNo`, `loadAttempts`). The demo uses the local
`courier.db` file, which is recreated on each run and excluded from version control.

## License

See [LICENSE](LICENSE).
