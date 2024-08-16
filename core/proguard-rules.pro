# Keep DI modules and their providers
-keep class com.fadhlansulistiyo.cinemadatabase.core.di.** { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.di.DatabaseModule { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.di.NetworkModule { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.di.DatabaseModule$* { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.ui.** { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.domain.** { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.data.Resource { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.data.remote.** { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.data.repository.** { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.data.local.** { *; }
-keep class com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.** { *; }

# Keep classes with @Inject constructors
-keepclasseswithmembernames class * {
     @javax.inject.Inject <init>(...);
}

# Keep classes with @Provides annotations
-keep class * {
     @dagger.Provides <methods>;
}

-ignorewarnings