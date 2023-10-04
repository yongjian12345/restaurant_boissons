package cstjean.mobile.restaurant

import android.content.Context
import androidx.room.Room
import cstjean.mobile.restaurant.database.BoissonDatabase
import cstjean.mobile.restaurant.boisson.Boisson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val DATABASE_NAME = "boisson-database"
class BoissonRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val database: BoissonDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            BoissonDatabase::class.java,
            DATABASE_NAME
        ).build()

    fun getBoissons(): Flow<List<Boisson>> = database.boissonDao().getBoissons()
    suspend fun getBoisson(id: UUID): Boisson = database.boissonDao().getBoisson(id)

    suspend fun addBoisson(boisson: Boisson) {
        database.boissonDao().addBoisson(boisson)
    }

    fun updateBoisson(boisson: Boisson) {
        coroutineScope.launch {
            database.boissonDao().updateBoisson(boisson)
        }
    }

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