# ProGuard rules for Jetpack Compose, Firebase, Room, and Coroutines
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# Keep data models and Room entities for reflection & serialization
-keepclassmembers class com.example.data.** { *; }

-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**

-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

-keepclassmembers class ** {
    @androidx.compose.runtime.Composable <methods>;
}

-dontwarn **
