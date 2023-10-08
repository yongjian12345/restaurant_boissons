package cstjean.mobile.restaurant


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.restaurant.boisson.Boisson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * ViewModel pour la liste des travaux.
 *
 * @property boissons La liste des travaux.
 *
 * @author Raphael ostiguy & Yong Jian Qiu
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


    suspend fun addBoisson(boissons: Boisson) {
        boissonRepository.addBoisson(boissons)
    }
}
