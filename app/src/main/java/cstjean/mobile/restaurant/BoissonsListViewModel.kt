package cstjean.mobile.restaurant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.restaurant.boisson.Boisson
import java.util.Date
import java.util.UUID


import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
private const val TAG = "BoissonsListViewModel"

/**
 * ViewModel pour la liste des travaux.
 *
 * @property boissons La liste des travaux.
 *
 * @author Gabriel T. St-Hilaire
 */
class BoissonsListViewModel : ViewModel() {
    val boissons = mutableListOf<Boisson>()

    init {
        viewModelScope.launch {
            Log.d(TAG, "Coroutine launched")
            boissons += loadBoissons()
            Log.d(TAG, "Coroutine finished")
        }
    }

    suspend fun loadBoissons(): List<Boisson> {
        val boissons = mutableListOf<Boisson>()
        delay(5000)
// Données de tests
        for (i in 0 until 100) {
            boissons += Boisson(
                UUID.randomUUID(),
                "Travail #$i",
                Produit.Biere,
                "Canada",
                "Molson",
            )
        }
        return boissons

    }
}
