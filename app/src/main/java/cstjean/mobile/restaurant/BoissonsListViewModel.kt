package cstjean.mobile.restaurant

import androidx.lifecycle.ViewModel
import cstjean.mobile.restaurant.boisson.Boisson
import java.util.Date
import java.util.UUID

/**
 * ViewModel pour la liste des travaux.
 *
 * @property boissons La liste des travaux.
 *
 * @author Gabriel T. St-Hilaire
 */
class BoissonsListViewModel : ViewModel() {
    private val boissonRepository = BoissonRepository.get()
    val boissons = boissonRepository.getBoissons()

   /* init {
        // Données de tests
        for (i in 0 until 100) {
            boissons += Boisson(
                UUID.randomUUID(),
                "Boisson #$i",
                Produit.values().random(),
                "Canada",
                "producteur",
                "photo"
            )
        }
    }*/
}
