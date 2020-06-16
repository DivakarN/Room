package com.sysaxiom.room

import android.content.Context
import androidx.room.*
import com.sysaxiom.garbagedriver.database.BinMasterConverters
import com.sysaxiom.room.database.LoginResponse

@Database(
    entities = [LoginResponse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BinMasterConverters::class)
abstract class SampleDatabase: RoomDatabase() {

    abstract fun getLoginDao(): LoginDao

    companion object {
        @Volatile
        private var instance: SampleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: createDatabase(
                    context
                ).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                SampleDatabase::class.java, "SampleDB.db").build()
    }
}
