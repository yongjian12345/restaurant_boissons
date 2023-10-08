package cstjean.mobile.restaurant.database

import androidx.room.TypeConverter
import cstjean.mobile.restaurant.Produit


/**
 * Convertisseur de type pour les boissons.
 */
class BoissonTypeConverters {
    @TypeConverter
    fun fromTypeProduit(produit: Produit): String {
        return produit.nom
    }
    @TypeConverter
    fun fromTypeProduitE(milisSinceEpoch: String): Produit {
        return Produit.fromNom(milisSinceEpoch)
    }
}
