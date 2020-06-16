package com.sysaxiom.room.repository

import com.sysaxiom.room.database.LoginResponse
import com.sysaxiom.room.SampleDatabase
import io.reactivex.Maybe

class LoginRepository(val db: SampleDatabase){

    suspend fun upsertLoginResponse(loginResponse: LoginResponse) {
        db.getLoginDao().upsertLoginResponse(loginResponse)
    }

    fun getLoginResponse(): Maybe<List<LoginResponse>> {
        return db.getLoginDao().getLoginResponse()
    }

    fun deleteLoginResponse() = db.getLoginDao().deleteLoginResponse()

}