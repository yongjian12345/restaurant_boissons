package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import cstjean.mobile.ecole.travail.Travail
import java.util.Date
import java.util.UUID

/**
 * ViewModel pour la liste des travaux.
 *
 * @property travaux La liste des travaux.
 *
 * @author Gabriel T. St-Hilaire
 */
class TravauxListViewModel : ViewModel() {
    val travaux = mutableListOf<Travail>()

    init {
        // Données de tests
        for (i in 0 until 100) {
            travaux += Travail(
                UUID.randomUUID(),
                "Travail #$i",
                Date(),
                i % 2 == 0
            )
        }
    }
}
