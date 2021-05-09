package school.cactus.succulentshop

import android.app.Application
import school.cactus.succulentshop.api.generateApi

class SucculentShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        generateApi(this)
    }
}