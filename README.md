## 🏋️‍♂️ Fitness Tracker

A modern and user-friendly Android app built using **Kotlin** and **Jetpack Compose**, designed to help users complete daily workouts, track calories burned, and calculate BMI. This project focuses on delivering a smooth fitness experience with clean UI and easy tracking.

### 📱 Preview of the Fitness Tracker

<div align="center">
  <img src="https://github.com/user-attachments/assets/42412f15-09f4-4a68-b078-253bd9f72959" width="25%" />
  <img src="https://github.com/user-attachments/assets/e08294e7-3b75-48d4-bf5d-e5525f2d59a6" width="25%" />
</div>

---

## 📲 Key Features

* Splash screen with app branding (3 seconds)
* Bottom navigation: Home, Progress, BMI, Account
* Daily workout plan summary: Exercises, Calories, Time, Level
* Real-time workout tracking and calorie updates
* Progress summary for burned calories

---

## 🧩 Code Highlights

* `MainActivity`: Launches splash screen and navigation
* `SplashScreen`: Timed transition using Coroutines
* `MainScreen`: Switches between Welcome and Workout
* `BottomNavigationBar`: Icon-based tab navigation
* `WelcomeScreen`: Daily plan overview + Start button
* `FitnessScreen`: Lists workouts with set tracking
* `WorkoutItem`: Displays workout card and updates calories

---

## 📦 Data Model

* `Workout`: Contains name, sets, calories per set

---

## 🛠 Tech Stack

* Kotlin + Jetpack Compose
* Material Design 3
* Coroutines

---

## 🔮 Future Scope

* Workout history & progress chart
* BMI calculator screen
* Profile management
* Daily workout notifications

---

## ▶️ Run Locally

1. Clone the repo
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or device
