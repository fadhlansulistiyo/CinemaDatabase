![App Screenshot](screenshots/main_banner.png)

# Cinema Database (CinDes)

Cinema Database (CinDes) is an Android application that showcases a collection of movies and TV shows, with data sourced directly from the TMDB API. Users can easily browse through popular titles, access detailed information, and discover new content. CinDes features a user-friendly interface, designed to provide a smooth and engaging browsing experience.

## Features
- Browse Movies & TV Shows: Explore a vast collection of popular and top-rated movies and TV shows.
- Detailed Information: View comprehensive details about each title, including synopsis, release date, ratings, and more.
- Trending & Popular People: Discover trending and popular actors, directors, and other celebrities, with detailed profiles for each.
- Search Functionality: Quickly find specific movies, TV shows.
- Responsive UI: Enjoy a smooth and intuitive user interface, optimized for various screen sizes.
- Watchlist: Add movies and TV shows to your watchlist for easy access later.
- Offline Access: Access previously viewed content on the home screen even without an internet connection.

## Prerequisites

- Android Studio
- TMDB Api Key (If you have your own)

### Setup Requirement 

1. **Clone the repository**:
    ```bash
    git clone https://github.com/fadhlansulistiyo/CinemaDatabase.git
    ```

2. **Add your TMDB Api Key**:
    - Obtain your API key from the [TMDB Api](https://developer.themoviedb.org).
    - Add the following line to your `build.gradle.kts (Module:core)`
      ```
      buildConfigField("String", "API_KEY", "\"your api key here\"")
      ```

3. **Build and Run the project**

## Tech Stack

- **Clean Architecture**: Organized into Data, Domain, and Presentation layers for modularity and separation of concerns.
- **Kotlin**: Primary programming language used for developing the application.
- **Dynamic Feature**: Implements a dynamic feature module for the Watchlist feature.
- **Library Module**: Core functionality is encapsulated within a reusable library module.
- **Coroutines**: Manages asynchronous programming.
- **Data Stream (Coroutines Flow)**: Streams asynchronous data.
- **Hilt**: Dependency Injection framework for managing app components.
- **Retrofit**: Type-safe HTTP client for API communication.
- **Room**: Local database solution for managing app data.
- **SQLCipher**: Adds encryption to the local database for enhanced security.
- **Jetpack Navigation Component**: Manages in-app navigation, including Bottom Navigation.
- **Glide**: Image loading and caching library.
- **ViewPager2**: Enhanced ViewPager for swiping movies.
- **Paging**: Handles efficient data pagination for lists.
- **ProGuard**: Obfuscation tool used to protect the app code.
- **Certificate Pinning**: Ensures secure connections by pinning SSL certificates.
- **Lottie**: Library for rendering animations.
- **Shimmer**: Adds shimmer effects to UI components.
- **LeakCanary**: Tool for detecting memory leaks during development.
- **ShowMoreText (by Sanjay Prajapat)**: Custom view for showing expandable text.

## Screenshots (Dark Theme)
## Screenshots (Light Theme)

## Acknowledgments

This project was developed as part of the capstone project for the [Dicoding](https://www.dicoding.com/academies/165) class - Becoming an Android Developer Expert (Menjadi Android Developer Expert). Special thanks to Dicoding for providing comprehensive learning resources and guidance throughout the course.

## CircleCI
[![fadhlansulistiyo](https://circleci.com/gh/fadhlansulistiyo/CinemaDatabase.svg?style=svg)](https://circleci.com/gh/fadhlansulistiyo/CinemaDatabase)
