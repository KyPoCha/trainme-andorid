package cz.cvut.fit.poliskyr.trainmeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings")
data class Training (
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "place") val place: String = "",
    @ColumnInfo(name = "timeTo") val timeTo: String = "",
    @ColumnInfo(name = "timeFrom") val timeFrom: String = "",
    @ColumnInfo(name = "trainerUsername") val trainerUsername: String = ""
)