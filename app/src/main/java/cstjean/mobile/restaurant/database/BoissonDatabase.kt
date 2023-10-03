package cstjean.mobile.restaurant.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cstjean.mobile.restaurant.boisson.Boisson
@Database(entities = [Boisson::class], version = 1, exportSchema = false)
@TypeConverters(BoissonTypeConverters::class)
abstract class BoissonDatabase : RoomDatabase(){
    abstract fun boissonDao(): BoissonDao
}