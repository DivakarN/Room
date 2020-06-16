package com.sysaxiom.garbagedriver.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sysaxiom.room.database.UserData
class BinMasterConverters {

    @TypeConverter
    fun binMastersToJson(binMasters: List<UserData>): String {
        val gson = Gson()
        val type = object : TypeToken<List<UserData>>() {}.type
        return gson.toJson(binMasters, type)
    }

    @TypeConverter
    fun jsonToBinMasters(json: String) : List<UserData>{
        val gson = Gson()
        val type = object : TypeToken<List<UserData>>() {}.getType()
        return gson.fromJson(json, type)
    }
}