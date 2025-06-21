package com.example.fitnessapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.ui.theme.FitnessAppTheme
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.fitnessapp.data.FitnessData


//Workout data class
//Containing 6 workout items
data class Workout(
    val name: String,
    val sets: Int,
    val reps: String,
    val caloriesPerSet: Int
)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FitnessData.initialize(this)
        setContent {
            FitnessAppTheme {
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    // Show splash screen for 2 seconds
                    SplashScreen {
                        showSplash = false
                    }
                } else {
                    // Show the main screen with navigation
                    MainScreen()
                }
            }
        }
    }
}


//Splash Screen of the app


//Splash screen for 2 seconds
@Composable
fun SplashScreen(onSplashEnd: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        onSplashEnd()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.dumbell),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Fitness App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}


//Main Screen of the app


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    Box {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onHomeClicked = { currentScreen = Screen.Home },
                    onProgressClicked = { currentScreen = Screen.Progress },
                    onBmiClicked = { currentScreen = Screen.Bmi }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                when (currentScreen) {
                    Screen.Home -> WelcomeScreen(onStartClicked = { currentScreen = Screen.Fitness }) // Update currentScreen to Fitness
                    Screen.Fitness -> FitnessScreen(context = LocalContext.current)
                    Screen.Progress -> ProgressScreen()
                    Screen.Bmi -> BmiScreen()
                }
            }
        }
    }
}
// Define Screen enum
enum class Screen {
    Home, Fitness,Progress, Bmi
}


//Progress screen of the app


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen() {
    val caloriesHistory = remember { mutableStateListOf<Pair<String, Int>>() }

    // Function to format the date
    fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        return date.format(formatter)
    }

    Box(modifier = Modifier.background(Color.White)) {
        // Fetch initial data (placeholder: adding 0 calories for the past 30 days)
        LaunchedEffect(key1 = Unit) {
            val today = LocalDate.now()
            // Check if today's date already exists in caloriesHistory
            val todayIndex = caloriesHistory.indexOfFirst { it.first == formatDate(today) }

            if (todayIndex != -1) {
                // If today's date exists, update its calorie value
                caloriesHistory[todayIndex] = Pair(formatDate(today), FitnessData.Companion.caloriesBurnedToday)
            } else {
                // If today's date doesn't exist, add it with calorie value
                caloriesHistory.add(Pair(formatDate(today), FitnessData.Companion.caloriesBurnedToday))
            }

            // Add previous days with 0 calories if not present
            for (i in 1..29) {
                val date = today.minusDays(i.toLong())
                val dateIndex = caloriesHistory.indexOfFirst { it.first == formatDate(date) }
                if (dateIndex == -1) {
                    caloriesHistory.add(Pair(formatDate(date), 0))
                }
            }

            // ... (rest of your code) ...
        }

        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = "Progress Report",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                    )
                })
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(
                    text = "30-Day Calories Burned History",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(caloriesHistory) { (date, calories) ->
                        Text(
                            text = "$date --> $calories kcal burnt",
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}


//BMI screen of the app


@Composable
fun BmiScreen() {
    var isMetric by remember { mutableStateOf(true) }
    var weightInput by remember { mutableStateOf("") }
    var heightInput by remember { mutableStateOf("") }
    var heightFeetInput by remember { mutableStateOf("") }
    var heightInchesInput by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UnitSelection(isMetric) { isMetric = it }
        Spacer(modifier = Modifier.height(16.dp))

        if (isMetric) {
            OutlinedTextField(
                value = weightInput,
                onValueChange = { weightInput = it },
                label = {
                    Text(
                        text = "Weight (kg)",
                        color = Color.Black,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = heightInput,
                onValueChange = { heightInput = it },
                label = {
                    Text(
                    "Height (cm)",
                          color = Color.Black,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        } else {
            OutlinedTextField(
                value = weightInput,
                onValueChange = { weightInput = it },
                label = { Text(
                    "Weight (lbs)",
                    color = Color.Black,
                ) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row {
                OutlinedTextField(
                    value = heightFeetInput,
                    onValueChange = { heightFeetInput = it },
                    label = { Text(
                        "Feet",
                        color = Color.Black,
                    ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = heightInchesInput,
                    onValueChange = { heightInchesInput = it },
                    label = { Text(
                        "Inches",
                        color = Color.Black,
                    ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val weight = weightInput.toDoubleOrNull()
            val height = heightInput.toDoubleOrNull()
            if (weight != null && height != null) {
                bmiResult =
                    calculateBMI(weight, height, isMetric, heightFeetInput, heightInchesInput)
            }
        }) {
            Text("Calculate BMI")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (bmiResult != null) {
            Text(
                text = "Your BMI : ${"%.2f".format(bmiResult)}",
                color = Color.Black,
            )
            getObesityLevel(bmiResult!!)
        }
    }
}
//function to show obesity level
@Composable
fun getObesityLevel(bmi: Double): Any {
    return when {
        bmi < 18.5 -> Text(
            text = "Under weight",
            color = Color.Black,
        )
        bmi in 18.5..24.9 -> Text(
            text = "Normal weight",
            color = Color.Black,
        )
        bmi in 25.0..29.9 -> Text(
            text = "Overweight",
            color = Color.Black,
        )
        bmi in 30.0..34.9 -> Text(
            text = "Obese (Class I)",
            color = Color.Black,
        )
        bmi in 35.0..39.9 -> Text(
            text = "Obese (Class II)",
            color = Color.Black,
        )
        else -> Text(
            text = "Obese (Class III)",
            color = Color.Black,
        )
    }
}
//function to select the unit of measurement
@Composable
fun UnitSelection(isMetric: Boolean, onUnitChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = isMetric,
            onClick = { onUnitChange(true) }
        )
        Text(
            text = "METRIC UNIT",
            fontSize = 16.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(
            selected = !isMetric,
            onClick = { onUnitChange(false) }
        )
        Text(
            text = "IMPERIAL UNIT",
            fontSize = 16.sp,
            color = Color.Black,
        )
    }
}
fun calculateBMI(weight: Double, height: Double, isMetric: Boolean, heightFeet: String, heightInches: String): Double {
    return if (isMetric) {
        val heightInMeters = height / 100
        weight / (heightInMeters * heightInMeters)
    } else {
        // Convert to metric and calculate
        val weightKg = weight * 0.453592
        val heightCm = (heightFeet.toDoubleOrNull() ?: 0.0) * 30.48 +
                (heightInches.toDoubleOrNull() ?: 0.0) * 2.54
        val heightInMeters = heightCm / 100
        weightKg / (heightInMeters * heightInMeters)
    }
}


//Bottom Navigation bar of the app


@Composable
fun BottomNavigationBar(
    onHomeClicked: () -> Unit,
    onProgressClicked: () -> Unit,
    onBmiClicked: () -> Unit
) {
    NavigationBar {
        Spacer(modifier = Modifier.width(32.dp))
        NavigationBarItem(
            selected = true, // You might need to manage the selected state
            onClick = onHomeClicked,
            icon = { Icon(painterResource(id = R.drawable.home), contentDescription = "Home") },
            label = { Text("Home",
                fontSize = 10.sp) }
        )
        Spacer(modifier = Modifier.width(44.dp))
        NavigationBarItem(
            selected = false, // You might need to manage the selected state
            onClick = onProgressClicked,
            icon = { Icon(painterResource(id = R.drawable.progress), contentDescription = "Progress") },
            label = { Text("Progress",
                fontSize = 10.sp) }
        )
        Spacer(modifier = Modifier.width(44.dp))
        NavigationBarItem(
            selected = false, // You might need to manage the selected state
            onClick = onBmiClicked,
            icon = { Icon(painterResource(id = R.drawable.bmi), contentDescription = "BMI") },
            label = { Text("BMI",
                fontSize = 10.sp) }
        )

        Spacer(modifier = Modifier.width(32.dp))
    }
}


//Home page of the app


@Composable
fun WelcomeScreen(onStartClicked: () -> Unit) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "FITNESS APP",
            fontSize = 48.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome, Priyanshu",
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Today's Workout Plan",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Workout summary UI like in your image
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Exercises - 12", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text(text = "Calories Burn - 200kcal", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text(text = "Time Required - 30mins", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text(text = "Level - Easy", fontSize = 19.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                // Start Button
                Button(onClick = onStartClicked) {
                    Text(text = "START", fontSize = 20.sp)
                }
            }
        }
    }
}


//Workout page of the app


@Composable
fun FitnessScreen(context:Context) {
    val workouts = listOf(
        Workout("Jumping Jacks", 2,"30 sec", 20),
        Workout("Lunges", 3, "30", 35),
        Workout("Pull Ups", 2,"15", 35),
        Workout("Push Ups", 3, "10",30),
        Workout("Squats", 3, "20", 25),
        Workout("Plank", 2, "40 sec", 40)
    )
    val workoutImages = mapOf(
        "Jumping Jacks" to R.drawable.jumping,
        "Lunges" to R.drawable.lunges,
        "Pull Ups" to R.drawable.pull,
        "Push Ups" to R.drawable.push,
        "Squats" to R.drawable.squat,
        "Plank" to R.drawable.plank,
    )

    var totalCalories by remember { mutableStateOf(FitnessData.caloriesBurnedToday) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Today's Workout Plan",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(workouts.size) { index ->
                val workout = workouts[index]
                val imageResource = workoutImages[workout.name] ?: R.drawable.dumbell
                WorkoutItem(
                    workout = workout,
                    onCaloriesBurned = {
                        totalCalories += it
                        FitnessData.Companion.caloriesBurnedToday = totalCalories
                        saveCaloriesToSharedPreferences(context, totalCalories) // Save to Shared Preferences
                    },
                    imageResource = imageResource
                )
            }
        }

        Text(
            text = "Total Calories Burned: $totalCalories kcal",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

    }
}


//Workout item of the app


@Composable
fun WorkoutItem(workout: Workout, onCaloriesBurned: (Int) -> Unit,imageResource: Int) {
    var completedSets by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            Row {
                Column(modifier = Modifier
                    .padding(16.dp)) {
                    Text(
                        text = workout.name,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Sets: ${workout.sets}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Reps: ${workout.reps}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Calories per set: ${workout.caloriesPerSet} kcal",
                        fontSize = 16.sp
                    )
                }
                Column(modifier = Modifier
                    .padding(16.dp)) {
                    Box {
                        Image(
                            painter = painterResource(id = imageResource), // Use imageResource here
                            contentDescription = workout.name,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        if (completedSets < workout.sets) {
                            completedSets++
                            onCaloriesBurned(workout.caloriesPerSet)
                        }
                    }
                ) {
                    Text(text = "Complete Set")
                }

                Box(Modifier.padding(8.dp)) {
                    Text(
                        text = "Completed: $completedSets/${workout.sets} sets",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}


fun saveCaloriesToSharedPreferences(context: Context, calories: Int) {
    val sharedPref = context.getSharedPreferences("fitness_prefs", Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putInt("calories_burned_today", calories)
        apply()
    }
}

fun loadCaloriesFromSharedPreferences(context: Context): Int {
    val sharedPref = context.getSharedPreferences("fitness_prefs", Context.MODE_PRIVATE)
    return sharedPref.getInt("calories_burned_today", 0) // Default to 0 if not found
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    FitnessAppTheme {
        WelcomeScreen(onStartClicked = {})
    }
}