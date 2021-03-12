package ge.lifecard.zcard.app

import android.app.Application
import android.content.Context

class LifeCardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStore.initialize(getSharedPreferences("_sp_", Context.MODE_PRIVATE))

    }


}