package school.cactus.succulentshop.db

import android.content.Context
import androidx.room.Room

private var _db: AppDatabase? = null

val db: AppDatabase get() = _db!!

// Accessing this will crash if done before calling createDatabase()
fun createDatabase(applicationContext: Context) {
    if (_db != null) return

    _db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "app-db"
    ).build()
}