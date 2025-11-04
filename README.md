# Location-Based Notes App

## Setup
Before building the app, make sure to **add the provided Google Maps API key inside the `zip` file** into your `local.properties` file

## Overview
The app allows users to:

- Register and log in using **Firebase Authentication**
- Create notes with a **title**, **description**, and **auto-attached location**
- View notes in both **List Mode** and **Map Mode**
- Swipe left on a note in **List Mode** to delete it
- Enjoy support for **light and dark themes**

## Known Limitations
- Only **Firebase Authentication** is used. There is no backend for storing user profiles.  
  → The user’s first name is saved locally using **SharedPreferences** and displayed on the main screen.
- In **Map Mode**, it is not possible to both display the note title/description *and* navigate to the note details screen on marker click.
- Due to time constraints, backend sync and advanced map info-window functionality were not implemented.