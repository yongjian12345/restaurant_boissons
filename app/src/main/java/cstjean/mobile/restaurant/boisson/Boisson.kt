package cstjean.mobile.restaurant.boisson

import cstjean.mobile.restaurant.Produit
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
data class Boisson(
    val id: UUID,
    val nom: String,
    val typeProduit: Produit,
    val paysOrigin: String,
    val producteur: String,
    var photoFilename: String? = null)