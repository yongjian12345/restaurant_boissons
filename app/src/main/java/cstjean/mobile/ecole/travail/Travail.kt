package cstjean.mobile.ecole.travail

import java.util.Date
import java.util.UUID

/**
 * Un travail scolaire.
 *
 * @property id Le ID du travail.
 * @property nom Le nom du travail.
 * @property dateRemise La date de remise du travail.
 * @property estTermine Si le travail est terminé.
 *
 * @author Gabriel T. St-Hilaire
 */
data class Travail(val id: UUID, val nom: String, val dateRemise: Date, val estTermine: Boolean)
