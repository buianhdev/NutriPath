# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project status

NutriPath is an Android nutrition‑tracking app in **very early scaffolding**. The full target architecture (entities, use cases, screens, Room DB) is described in two Vietnamese design docs in the repo root — treat these as the source of truth for what to implement and why:

- `NutriPath_AI_MVP_v1_Plan.md` — MVP v1 scope and all nutrition formulas (BMR Mifflin‑St Jeor, TDEE, target calories, macro targets, goal estimation, quick‑add scaling).
- `nutripath_architecture_analysis_mvp_v1 (2).md` — domain entities, use cases, repository interfaces, DB tables, screen flows, architecture decisions.

Today only base MVI/use‑case classes, theme files, and `MainActivity` (Hello‑world Compose) exist. Most packages under `vn.anhbt.nutripath.{data,domain,presentation,di}` are empty placeholders matching the planned layout from the architecture doc.

## Build & test

This project uses the Gradle wrapper, Kotlin DSL, and AGP 9 with Kotlin 2.0.21. The Gradle daemon runs on JDK 21 (`gradle/gradle-daemon-jvm.properties`), and the Android module compiles to JVM 11.

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

Compose UI tests live in `app/src/androidTest/...` and run via `connectedAndroidTest`.

Module/SDK config (`app/build.gradle.kts`): namespace `vn.anhbt.nutripath`, `compileSdk = release(36) { minorApiLevel = 1 }` (AGP 9 minor‑SDK API), `minSdk = 24`, `targetSdk = 36`. Dependency versions are centralized in `gradle/libs.versions.toml`.

## Architecture

Clean Architecture + MVI. The package tree under `vn.anhbt.nutripath/` is already laid out for the planned layers:

```
core/           mvi/ (BaseViewModel, Contract), common/, util/, validation/
domain/         model/, repository/ (interfaces), usecase/{nutrition,meal,food,summary}/
data/           local/{database,entity,dao,datastore}/, repository/ (impls), mapper/, seed/
presentation/   onboarding/, today/, addmeal/, profile/, navigation/, common/, theme/
di/             Koin modules
```

### MVI contract (`core/mvi/`)

`BaseViewModel<S: UIState, I: MviIntent, E: MviEvent>(initialState)` exposes three things:

- `state: StateFlow<S>` — current UI state, mutated via `updateState { copy(...) }` or `setState(...)`.
- `event: Flow<E>` — one‑shot effects sent through a `Channel.BUFFERED`, consumed via `sendEvent(...)`.
- `dispatchIntent(intent: I)` is the only public entry point; subclasses implement `onIntent(intent: I)`.

`UIState`, `MviIntent`, `MviEvent` are marker interfaces in `core/mvi/Contract.kt`. Each screen package defines its own state/intent/event types implementing these markers.

### Use case contract (`domain/usecase/`)

Two base classes for use cases:

- `BaseUseCase<R, Q>` — synchronous; subclasses override `execute(params: R): Q` and callers invoke via `invoke(params)`.
- `BaseUseCaseFlow<R, Q>` — returns `Flow<Q>`; `invoke` wraps `execute` with a `.catch {}` swallow, so subclasses should not rely on errors propagating downstream unless this is changed.
- `NoParams` data object — pass as `params` when a use case takes no input.

### Domain model (planned, not yet coded)

Per the architecture doc, the canonical entities are `UserProfile`, `NutritionGoal`, `NutritionPlan`, `Food`, `MealEntry`, and the value object `MacroBreakdown`. `DailyLogSummary` is a **computed read model** (not persisted) — always derive it from active `NutritionPlan` + `MealEntry`s for the day. Versioning rule: editing profile or goal creates a **new** `NutritionGoal` and **new** active `NutritionPlan`; previous rows get `isActive = false`. Keep `profileSnapshot`/`goalSnapshot` on `NutritionPlan` so historical plans stay correct after profile edits.

### Nutrition calculations

All formulas (BMR, TDEE, target calories per goal speed, protein/fat/carb targets, goal weeks estimation, quick‑add gram scaling) are specified with Kotlin examples in `NutriPath_AI_MVP_v1_Plan.md` §4. Implement them in the domain layer (e.g. `domain/usecase/nutrition/`) and match the formulas exactly — they are part of the product spec.

## Tech stack

- Kotlin 2.0.21, Jetpack Compose (BOM `2024.09.00`), Material 3, edge‑to‑edge enabled.
- Koin 4.0.0 for DI (`koin-android`, `koin-androidx-compose`). DI modules belong in `di/`.
- Theme entry point: `NutriPathTheme` in `presentation/theme/Theme.kt` (dynamic color on Android 12+).

## Working in this repo

- The two `.md` design docs are written in Vietnamese; rely on them for entity field lists, repository signatures, screen flows, and the prioritized implementation order (architecture doc §10).
- When adding a feature, follow the planned package layout above instead of inventing new top‑level packages — most of the directories already exist as empty scaffolding.
- Tests in `ExampleUnitTest.kt` / `ExampleInstrumentedTest.kt` are AGP boilerplate; replace them as real code lands rather than building on them.
