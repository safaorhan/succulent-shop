package school.cactus.succulentshop

import android.app.Application
import school.cactus.succulentshop.api.generateApi
import school.cactus.succulentshop.db.createDatabase

class SucculentShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        generateApi(this)
        createDatabase(this)
    }
}