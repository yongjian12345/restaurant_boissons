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
 * @property typeProduit Le type de produit de la boisson.
 * @property paysOrigin Le pays d'origine de la boisson.
 * @property producteur Le producteur de la boisson.
 *
 * @author Raphael Ostiguy & Yong Jian Qiu
 */

@Entity
data class Boisson(
    @PrimaryKey val id: UUID,
    val nom: String,
    val typeProduit: Produit,
    val paysOrigin: String,
    val producteur: String,
    var photoFilename: String? = null)
