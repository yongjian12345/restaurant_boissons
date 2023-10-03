package cstjean.mobile.restaurant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.restaurant.boisson.Boisson
import java.util.Date
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
    private val boissonRepository = BoissonRepository.get()
    private val _boissons: MutableStateFlow<List<Boisson>> = MutableStateFlow(emptyList())
    val boissons: StateFlow<List<Boisson>> = _boissons

    init {
        viewModelScope.launch {
            boissonRepository.getBoissons().collect {
                _boissons.value = it
            }
        }
    }

    suspend fun loadBoissons() {
// Données de tests

        for (i in 0 until 100) {
            val boissons = Boisson(
                UUID.randomUUID(),
                "Travail #$i",
                Produit.Biere,
                "Canada",
                "Molson",
            )
            addBoisson(boissons);
        }
    }

    suspend fun addBoisson(boissons: Boisson) {
        boissonRepository.addBoisson(boissons)
    }
}
