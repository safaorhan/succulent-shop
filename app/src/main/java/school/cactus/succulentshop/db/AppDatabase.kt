package school.cactus.succulentshop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import school.cactus.succulentshop.db.product.ProductDao
import school.cactus.succulentshop.db.product.ProductEntity


@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}