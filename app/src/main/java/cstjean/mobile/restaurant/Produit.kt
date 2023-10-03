package cstjean.mobile.restaurant

enum class Produit ( val nom: String){
    Vin("Vin"),
    Spiritueux("Spiritueux"),
    Aperitif("Apéritif"),
    Biere("Bière"),
    Autre( "Autre");

    companion object {
        fun fromNom(nom: String): Produit? {
            for (value in values()) {
                if (value.nom == nom) {
                    return value
                }
            }
            return null
        }
    }
}