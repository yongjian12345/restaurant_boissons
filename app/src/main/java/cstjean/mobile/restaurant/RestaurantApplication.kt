package cstjean.mobile.restaurant

import android.app.Application

/**
 * Application qui contient un seul Fragment.
 * @property nom Le nom du produit
 */
class RestaurantApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BoissonRepository.initialize(this)
    }
}