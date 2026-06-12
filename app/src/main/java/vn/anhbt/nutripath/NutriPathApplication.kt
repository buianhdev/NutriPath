package vn.anhbt.nutripath

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import vn.anhbt.nutripath.di.dataModule
import vn.anhbt.nutripath.di.domainModule
import vn.anhbt.nutripath.di.viewModelModule

class NutriPathApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NutriPathApplication)
            modules(dataModule, domainModule, viewModelModule)
        }
    }

}