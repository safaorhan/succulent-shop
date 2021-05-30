package school.cactus.succulentshop.db.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "high_res_image_url") val highResImageUrl: String,
)
