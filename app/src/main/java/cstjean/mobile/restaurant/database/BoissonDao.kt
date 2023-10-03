package cstjean.mobile.restaurant.database

import androidx.room.Dao
import androidx.room.Query
import cstjean.mobile.restaurant.boisson.Boisson
import java.util.UUID
import androidx.room.Insert
import kotlinx.coroutines.flow.Flow

@Dao
interface BoissonDao {
    @Query("SELECT * FROM boisson")
    fun getBoissons(): Flow<List<Boisson>>
    @Query("SELECT * FROM boisson WHERE id=(:id)")
    suspend fun getBoisson(id: UUID): Boisson

    @Insert
    suspend fun addBoisson(boisson: Boisson)
}