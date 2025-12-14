# ☆ Towns.IO Mobile Codebase ☆

> "Puerto Rico, I love that."

Welcome to the source code repository for **Towns.IO**! This application is a mobile quiz game designed to test knowledge of the 78 municipalities of Puerto Rico, developed for the SICI 4185 course (Introducción a la Programación de Dispositivos Móviles).

The application is a triple-mode quiz, challenging users to identify towns based on their official **Flag**, **Shield**, or unique **Cognomento** (slogan). It features a robust high-score system filtered by mode and difficulty.

## ☆ What's Inside?

Here is a breakdown of the core files, directories, and data structures you'll find in the `app/src/main/java/com/alexis_benejan/townsio/` package:

| File/Folder | Purpose | Key Features |
| :--- | :--- | :--- |
| **Activities & Navigation** | | |
| `MainActivity.kt` | **Menu Hub.** Controls the main screen, manages the selection of **Game Mode** and **Difficulty**, and directs users to the quiz or high scores. | `startDifficultySelection()`, `launchQuizActivity()`. |
| `QuizActivity.kt` | **Quiz Engine.** The core game loop controller. Handles question presentation, manages time, processes answers, and implements multimedia feedback. | **`handleAnswer()`**, **`loadNewQuestion()`**, **`animate()`** (Circular Reveal Animation). |
| `HighScoresActivity.kt` | **Records Display.** Shows the user's latest score and the historical Top 5 list. | **Double Filtering** (Mode and Difficulty), `formatTime()`. |
| `AboutActivity.kt` | **Developer Info.** Displays attribution and handles external link navigation (Ko-fi, GitHub). | `openWebPage()`. |
| **Data & Persistence** | | |
| `Town.kt` | **Data Model.** Defines the structured data for a single Puerto Rican municipality. | Stores `flagResource`, `shieldResource`, and `cognomento`. |
| `TownDataSource.kt` | **Data Source.** Provides the complete, static list of all **78 Puerto Rican municipalities** and their assets. | `getTowns()`. |
| `HighScoresManager.kt` | **Persistence Layer.** Manages saving, retrieving, and sorting high scores. Uses Shared Preferences/Gson. | **Sorting by Score, Mode, Difficulty, and Time.** |
| `HighScoreEntry.kt` | **Record Structure.** The data structure used for persistent score records. | Includes `userName`, `timeMillis`, `mode`, and `difficulty`. |
| **Definitions** | | |
| `GameMode.kt` | **Game Modes.** Defines the three available quiz modes. | Stores short and long title resources for display. |
| `Difficulty.kt` | **Difficulty Levels.** Defines difficulty tiers and the corresponding number of questions (e.g., EASY=10, PATRIOT=78). | `maxQuestions`. |

## ☆ Changelog

This log tracks key feature additions and structural changes across the development cycles of the Towns.IO Quiz Application.

| Version | Date | Changes |
| :--- | :--- | :--- |
| **V1.0.0a** | 2025-12-14 | **[ALPHA RELEASE] Codebase Completion & QA.** Final implementation of the quiz engine. **Implemented strict game logic:** If the user gets a question wrong, they must advance immediately, counting as a single failed attempt. **Completed Animation:** Added circular reveal/hide animation to `QuizActivity.kt` for visual transitions. |
| **V0.9.5** | 2025-12-14 | **Quiz Engine Integration & Flow Fixes:** Finalized `QuizActivity.kt` logic. Integrated `TownDataSource.kt` and `Town.kt`. **Critical Flow Fix:** Corrected the intent passing of `GameMode` from `MainActivity` to `QuizActivity` (passing name string instead of ordinal). |
| **V0.9.0** | 2025-12-14 | **Front-End Navigation Complete:** Implemented **`MainActivity.kt`** with Mode/Difficulty selectors and navigation to `HighScoresActivity` and `AboutActivity`. Connected external links (Ko-fi/GitHub) in `AboutActivity.kt`. |
| **V0.8.0** | 2025-12-14 | **High Scores Module Finalized:** Completed **`HighScoresActivity.kt`** and **`HighScoresManager.kt`** with double filtering (Mode and Difficulty), time formatting (`MM:SS`), and saving of user names. |
| **V0.7.0** | 2025-12-13 | **Data Structure Foundation:** Defined essential data classes: `TownData.kt`, `TownDataSource.kt` (78 entries implemented), `GameMode.kt`, and `Difficulty.kt`. |
| **<V0.6.0** | 2025-12-13 | **Testing and Tinkering:** Worked on the different methods trying to find the best option for my skill level. |

## ☆ Implemented Functionality & Features

The application fully implements the core requirements across the entire assignment:

* **Triple-Mode Quiz:** Supports identifying towns by **Flags**, **Shields**, and **Cognomentos**.
* **High Scores:** Records are filtered by **Game Mode** and **Difficulty**.
* **Persistence:** Uses Shared Preferences (via `HighScoresManager`) to store historical scores, including **Time** and **User Name**.
* **Multimedia & Animation:** Integration of custom sounds (`R.raw/`) and the **`ViewAnimationUtils.createCircularReveal`** animation for engaging visual feedback.
* **Difficulty Scaling:** Includes three tiers of difficulty with varying question counts (`EASY` 10, `HARD` 35, `PATRIOT` 78).

## ☆ Requirements

To compile and run this mobile application, ensure your development environment is set up with:

* **Android Studio** (Latest version recommended)
* **Kotlin Development Kit (KDK)**
* **Android SDK** (API 28+ recommended)
* **Dependencies:** Material Components and Google Gson (`com.google.code.gson`) for object persistence.
* **Resources:** All 156 image assets (`drawable/`) and sound files (`raw/`) are required to run the quiz without errors.

## ☆ Coding Style & Attribution

All Kotlin code across this project follows a specific stylish header and clean structure. Please keep these headers intact if you modify or redistribute the source files.

Here is an example of the file header format:

```kotlin
/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: Example.kt
☆ Date: 2025-11-30
☆
*/
```
---

## ☆ License
- ☆ This code is licensed under the MIT License!
- ☆ The image resources used in tis project however are All Rights Reserved. | *All Flags and Shields were downloaded from [wikimedia.org](https://commons.wikimedia.org)*

- A credit to MelodyHSong is always appreciated.

---

*"The best way to finish SICI 4185." - MelodyHSong*
