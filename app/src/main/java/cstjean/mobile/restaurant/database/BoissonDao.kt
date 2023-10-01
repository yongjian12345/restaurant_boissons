package cstjean.mobile.restaurant.database

import androidx.room.Dao
import androidx.room.Query
import cstjean.mobile.restaurant.boisson.Boisson
import java.util.UUID

@Dao
interface BoissonDao {
    @Query("SELECT * FROM boisson")
    fun getBoissons(): List<Boisson>
    @Query("SELECT * FROM boisson WHERE id=(:id)")
    fun getBoisson(id: UUID): Boisson
}