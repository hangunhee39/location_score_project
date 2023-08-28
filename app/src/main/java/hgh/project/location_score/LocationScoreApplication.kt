package hgh.project.location_score

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import hgh.project.location_score.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LocationScoreApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger(
                Level.NONE
            )
            androidContext(this@LocationScoreApplication)
            modules(appModule)
        }
    }
}