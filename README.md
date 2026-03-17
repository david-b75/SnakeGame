# Snake Game

A simple Snake game for Android built with Kotlin and Jetpack Compose.

## Features
- Classic snake gameplay on a 20x20 grid
- Touch-based controls (swipe to move)
- Score tracking (+10 points per food eaten)
- Game over detection (collision with self)
- Wrapping edges (snake wraps around grid boundaries)
- Material Design 3 UI with dark theme

## How to Play
1. Tap "Start Game" to begin
2. Swipe up, down, left, or right to move the snake
3. Eat the red food to grow and gain points
4. Avoid hitting yourself
5. Game wraps around edges

## Build Instructions
1. Clone the repository: `git clone https://github.com/david-b75/SnakeGame.git`
2. Open in Android Studio Hedgehog or newer
3. Wait for Gradle to sync
4. Connect an Android device or start an emulator
5. Click **Run** or press Shift+F10

## Build APK for Release
1. Click **Build → Generate Signed Bundle / APK**
2. Choose **APK** (or **App Bundle** for Google Play)
3. Follow the signing wizard to create a keystore
4. APK will be generated in `app/build/outputs/apk/release/`
5. Install with: `adb install app-release.apk`

## Requirements
- Android 8.0+ (API 26+)
- Kotlin 1.9.24+
- Gradle 8.5.2+

## Technologies
- Jetpack Compose for UI
- Kotlin Coroutines for game loop
- Material Design 3
