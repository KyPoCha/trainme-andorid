package cz.cvut.fit.poliskyr.trainmeapp.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import cz.cvut.fit.poliskyr.trainmeapp.util.BitmapTypeConverter

@Entity(tableName = "trainers")
@TypeConverters(BitmapTypeConverter::class)
data class Trainer (
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "username") val username: String = "",
    @ColumnInfo(name = "reviewValue") val reviewValue: Int = 0,
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "telephone") val telephone: String = "",
    @ColumnInfo(name = "priceForOneTraining") val priceForOneTraining: String = "",
    @ColumnInfo(name = "priceForMonthTraining") val priceForMonthTraining: String = "",
    @ColumnInfo(name = "dateOfBirthday") val dateOfBirthday: String = "",
    @ColumnInfo(name = "image") var image: String? = null,
)
