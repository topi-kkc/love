-keepattributes *Annotation*
-keep class kotlin.** { *; }
-keep class androidx.** { *; }
-keep class com.example.todoapp.** { *; }
-dontwarn kotlin.**
-dontwarn androidx.**

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *