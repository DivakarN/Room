package com.sysaxiom.room

import androidx.room.*
import com.sysaxiom.room.database.LoginResponse
import io.reactivex.Maybe

@Dao
interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertLoginResponse(loginResponse: LoginResponse)

    @Query("SELECT * FROM login_response")
    fun getLoginResponse(): Maybe<List<LoginResponse>>

    @Query("DELETE FROM login_response")
    fun deleteLoginResponse()

}
