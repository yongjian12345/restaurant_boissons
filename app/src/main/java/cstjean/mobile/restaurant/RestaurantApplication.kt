package cstjean.mobile.restaurant

import android.app.Application

class RestaurantApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BoissonRepository.initialize(this)
    }
}