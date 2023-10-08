package cstjean.mobile.restaurant.boisson

import androidx.room.Entity
import androidx.room.PrimaryKey
import cstjean.mobile.restaurant.Produit
import java.util.UUID

/**
 * Une boissons.
 *
 * @property id Le ID de la boisson.
 * @property nom Le nom de la boisson.
 * @property dateRemise La date de remise du travail.
 * @property estTermine Si le travail est terminé.
 *
 * @author Gabriel T. St-Hilaire
 */
@Entity
data class Boisson(
    @PrimaryKey val id: UUID,
    val nom: String,
    val typeProduit: Produit,
    val paysOrigin: String,
    val producteur: String,
    var photoFilename: String? = null)