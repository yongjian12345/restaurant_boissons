package cstjean.mobile.restaurant.database

import androidx.room.TypeConverter
import cstjean.mobile.restaurant.Produit
import java.util.Date
class BoissonTypeConverters {
    @TypeConverter
    fun fromTypeProduit(produit: Produit): String {
        return produit.nom
    }
    @TypeConverter
    fun fromTypeProduitE(milisSinceEpoch: String): Produit {
        return Produit.valueOf(milisSinceEpoch)
    }
}