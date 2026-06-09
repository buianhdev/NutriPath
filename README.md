# NutriPath

An Android nutrition‑tracking app. Set your profile and goal, get a daily calorie/macro target, log meals, and see today's remaining nutrition at a glance.

> **Status:** very early scaffolding. Architecture skeleton and base MVI/use‑case classes are in place; most screens and data layer are not yet implemented. See [`NutriPath_AI_MVP_v1_Plan.md`](NutriPath_AI_MVP_v1_Plan.md) for the MVP scope and nutrition formulas.

## Tech stack

- **Language:** Kotlin 2.0.21
- **UI:** Jetpack Compose (BOM 2024.09.00), Material 3, edge‑to‑edge
- **Architecture:** Clean Architecture + MVI
- **DI:** Koin 4.0.0
- **Persistence:** Room (KSP)
- **Build:** AGP 9, Gradle wrapper, JDK 21 daemon, JVM 11 target
- **SDK:** `minSdk = 24`, `targetSdk = 36`

## Module layout

```
app/src/main/java/vn/anhbt/nutripath/
├── core/          mvi/ (BaseViewModel, Contract), common/, util/, validation/
├── domain/        model/, repository/ (interfaces), usecase/{nutrition,meal,food,summary}/
├── data/          local/{database,entity,dao,datastore}/, repository/ (impls), mapper/, seed/
├── presentation/  onboarding/, today/, addmeal/, profile/, navigation/, common/, theme/
└── di/            Koin modules
```

## Build & run

Requires JDK 21 (Gradle daemon) and an Android device or emulator on API 24+.

```bash
./gradlew assembleDebug              # build debug APK
./gradlew :app:installDebug          # install on connected device/emulator
./gradlew test                       # all unit tests
./gradlew :app:testDebugUnitTest     # debug unit tests only
./gradlew connectedAndroidTest       # instrumented tests (needs device/emulator)
./gradlew lint                       # Android lint
```

Run a single unit test:

```bash
./gradlew :app:testDebugUnitTest --tests "vn.anhbt.nutripath.ExampleUnitTest.addition_isCorrect"
```

## Design docs

The two Vietnamese design docs in the repo root are the source of truth:

- [`NutriPath_AI_MVP_v1_Plan.md`](NutriPath_AI_MVP_v1_Plan.md) — MVP scope and all nutrition formulas (BMR Mifflin‑St Jeor, TDEE, target calories, macro targets, goal estimation, quick‑add scaling).
- `nutripath_architecture_analysis_mvp_v1 (2).md` — domain entities, use cases, repository interfaces, DB tables, screen flows.

Contributor guidance for AI assistants lives in [`CLAUDE.md`](CLAUDE.md).
