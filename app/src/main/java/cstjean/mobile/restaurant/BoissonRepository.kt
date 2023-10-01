package cstjean.mobile.restaurant

import android.content.Context
import androidx.room.Room
import cstjean.mobile.restaurant.database.BoissonDatabase
import cstjean.mobile.restaurant.boisson.Boisson
import java.util.UUID

private const val DATABASE_NAME = "boisson-database"
class BoissonRepository private constructor(context: Context){
    private val database: BoissonDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            BoissonDatabase::class.java,
            DATABASE_NAME
        ).build()

    fun getBoissons(): List<Boisson> = database.boissonDao().getBoissons()
    fun getBoisson(id: UUID): Boisson = database.boissonDao().getBoisson(id)

    companion object {
        private var INSTANCE: BoissonRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BoissonRepository(context)
            }
        }
        fun get(): BoissonRepository {
            return INSTANCE ?:
            throw IllegalStateException("TravailRepository doit être initialisé.")
        }
    }
}