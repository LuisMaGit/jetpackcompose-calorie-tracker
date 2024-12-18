## Calorie Tracker
Simple Calorie Tracker Android App. 

The application allows the user to track the calories of their meals based on their goals. To achieve this, once the app is installed, the user will be submitted to a simple form to find out the user's goals based on their physique. Once completed, the user can search and submit the food eaten at each meal and the app will calculate and display (with simple percentage graphs) the nutrients consumed.

<p align="center">
<img src="https://user-images.githubusercontent.com/70621340/220591756-0ef1e9f5-dc82-4bad-8ebd-8cbc1bdfc6e8.png"  width="170" height="340">
<img src="https://user-images.githubusercontent.com/70621340/220593674-83f294a1-d2b7-49d9-b693-d0f7d9323c7a.png"  width="170" height="340">
<img src="https://user-images.githubusercontent.com/70621340/220593752-c8a951e7-c3b7-4fc5-b9ec-e088f1ff309b.png"  width="170" height="340">
<img src="https://user-images.githubusercontent.com/70621340/220593822-c3fe8748-e176-4a95-ae3d-dba74445786b.png"  width="170" height="340">
</p>

## Architecture
The app is build as a standard multi-modular clean architecture project:

<img src="https://user-images.githubusercontent.com/70621340/220617652-46752115-3622-4968-a39f-f982e73ef3b9.png" width="800" height="600"/>

## Stack
* Jetpack Compose/Compose View Model/Compose Navigation (ui)
* Hilt-Dagger (di)
* Ktor (network)
* SqlDelight (db)
* DataStore (preferences)
* Kotlin serialization (json manipulation)
* JUnit 4 (test)
* Mockk (test)
* Turbine (test)

## Unit tests
Run the configuration in the picture bellow to run all the units tests in the app (full covarage in the View Models of Tracker feature)

<img src="https://user-images.githubusercontent.com/70621340/220621874-a82210cd-5e6a-4866-b0bc-0b685c552797.png" width="300" height="200"/>
<img src="https://user-images.githubusercontent.com/70621340/220623732-30423b3d-cc97-460a-ba18-67c5d08dc276.png" width="500" height="400"/>





