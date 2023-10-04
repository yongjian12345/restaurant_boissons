package cstjean.mobile.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.restaurant.boisson.Boisson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.update

class BoissonViewModel(boissonId: UUID) : ViewModel() {
    private val boissonRepository = BoissonRepository.get()
    private val _boisson: MutableStateFlow<Boisson?> = MutableStateFlow(null)
    val boisson: StateFlow<Boisson?> = _boisson
    init {
        viewModelScope.launch {
            _boisson.value = boissonRepository.getBoisson(boissonId)
        }
    }

    fun updateBoisson(onUpdate: (Boisson) -> Boisson) {
        _boisson.update { oldBoisson ->
            oldBoisson?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        boisson.value?.let { boissonRepository.updateBoisson(it) }
    }
}

class BoissonViewModelFactory(private val boissonId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BoissonViewModel(boissonId) as T
    }
}