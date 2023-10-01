package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import cstjean.mobile.ecole.boisson.Boisson
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
    val boissons = mutableListOf<Boisson>()

    init {
        // Données de tests
        for (i in 0 until 100) {
            boissons += Boisson(
                UUID.randomUUID(),
                "Boisson #$i",
                Date(),
                i % 2 == 0
            )
        }
    }
}
