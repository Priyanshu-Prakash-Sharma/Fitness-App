**Name**: Priyanshu Prakash Sharma                 
**Company**: CODTECH IT SOLUTIONS                             
**ID**: CT6WDS1818                               
**Domain**: Android Development                              
**Duration**: August to October 2024        

**Preview of the Fitness app**
![Fitness_app1](https://github.com/user-attachments/assets/42412f15-09f4-4a68-b078-253bd9f72959)
![Fitness_app2](https://github.com/user-attachments/assets/e08294e7-3b75-48d4-bf5d-e5525f2d59a6)
## Fitness App - Overview

### Project Description
The **Fitness App** is a modern Android application developed using **Kotlin** and **Jetpack Compose**, aimed at helping users track and complete daily workout routines. The app provides a simple yet effective user interface where users can view their workout plans, start their sessions, and track their progress. The app also includes a splash screen, bottom navigation bar, and multiple screens for different sections such as workouts, progress, and BMI calculation. 

### Features

1. **Splash Screen**:
   - The app includes an initial splash screen that is displayed for 3 seconds. It showcases the app logo and title, providing a clean introduction to the app.

2. **Bottom Navigation Bar**:
   - The app has a **Bottom Navigation Bar** allowing users to navigate between different sections like **Home**, **Progress**, **BMI**, and **Account**. This provides easy access to various functionalities and allows for smooth navigation.

3. **Workout Plan**:
   - Users are greeted with a welcome screen that shows the user’s name and the details of their **workout plan** for the day. The plan summary includes:
     - **Number of Exercises**
     - **Total Calories to Burn**
     - **Time Required**
     - **Workout Level**
   - Users can start their workout by pressing the **Start** button.

4. **Workout Tracker**:
   - After starting the workout, the app displays a list of **workouts** with sets and calories to be burned. Each workout is presented in a card with:
     - The **workout name** (e.g., Push Ups, Squats)
     - **Number of sets**
     - **Calories burned per set**
     - **Workout image**
   - Users can mark each set as completed, and the app will automatically update the total **calories burned**.

5. **Progress Tracking**:
   - The app keeps track of the total **calories burned** during a workout session. Users can view this total at the bottom of the workout screen after completing each set.

### Code Overview

1. **MainActivity**:
   - The main entry point of the app that sets up the app's content using **Jetpack Compose**. The splash screen is displayed for 3 seconds before transitioning to the main content, which includes the navigation and the fitness screen.

2. **SplashScreen**:
   - This composable function displays the app logo and title in a centered layout with a light gray background. It uses **LaunchedEffect** to control the timing of the splash screen, transitioning to the main screen after 3 seconds.

3. **MainScreen**:
   - The primary screen where users can either view the welcome screen or the fitness workout plan, depending on whether they have started their workout. It also includes the **Bottom Navigation Bar** for navigation.

4. **BottomNavigationBar**:
   - This composable provides navigation items, including icons for **Home**, **Progress**, **BMI**, and **Account**. Each item uses an icon and label and can be extended to handle click events for different sections of the app.

5. **WelcomeScreen**:
   - This screen greets the user and displays a summary of the daily workout plan. It includes details about the number of exercises, calories to burn, time required, and difficulty level. The **Start** button triggers the beginning of the workout session.

6. **FitnessScreen**:
   - The main workout screen where users can see a list of workouts for the day. Each workout includes the name, number of sets, and calories burned per set. Users can complete sets and the app will track the total calories burned in real-time.

7. **WorkoutItem**:
   - This composable is used to represent an individual workout. It displays the workout name, sets, and calories per set, along with an image of the workout. The user can click the **Complete Set** button to mark a set as completed, which updates the **total calories burned**.

### Data Models

1. **Workout**:
   - A simple data class representing a workout with three properties:
     - **name**: The name of the workout (e.g., Push Ups, Squats)
     - **sets**: The number of sets for the workout.
     - **caloriesPerSet**: The number of calories burned for each set.

### User Interface Design

- **Splash Screen**: Light gray background with a centered logo and app title.
- **Main Screen**: The main content includes a navigation bar at the bottom and a fitness screen that updates dynamically.
- **Workout Cards**: Each workout is displayed in a card with relevant details, including a button to track set completion.
- **Typography**: Bold and clear text is used for workout information, calorie tracking, and navigation labels, making the app user-friendly and easy to read.

### Tools and Technologies

- **Kotlin**: The primary programming language for the project.
- **Jetpack Compose**: Used for building the UI in a declarative style.
- **Material 3**: The app utilizes Material Design 3 for consistent UI components.
- **Coroutines**: Used in the splash screen for managing delay transitions.

### Future Enhancements

1. **Progress Section**: Implement a feature where users can track their workout history and view their fitness progress over time.
2. **BMI Calculator**: Integrate a BMI calculator in the **BMI** section of the bottom navigation bar.
3. **User Profiles**: Add a section under **Account** for user profile management, where users can update their personal information and view their fitness statistics.
4. **Notifications**: Implement push notifications to remind users of their daily workout routines.

### How to Run the Project

1. Clone the repository from GitHub.
2. Open the project in **Android Studio**.
3. Sync Gradle files and install any required dependencies.
4. Run the app on an emulator or physical Android device.

