package com.sysaxiom.garbagedriver.application

import android.app.Application
import com.sysaxiom.garbagedriver.ui.login.MainViewModelFactory
import com.sysaxiom.room.SampleDatabase
import com.sysaxiom.room.repository.LoginRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class GarbageDriverApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GarbageDriverApplication))
        bind() from singleton { SampleDatabase(instance()) }
        bind() from singleton { LoginRepository(instance()) }

        bind() from provider { MainViewModelFactory(instance()) }
    }

}