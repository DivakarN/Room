package com.sysaxiom.room.database

import androidx.room.*

const val USER_ID = 1

@Entity(tableName = "login_response")
data class LoginResponse (
    val success: Boolean,
    val status: Long,
    @Embedded
    val data: LoginData
) {
    @PrimaryKey(autoGenerate = false)
    var userId = USER_ID
}

data class LoginData (
    val userData: List<UserData>
)

data class UserData (
    val id: String,
    val name: String
)

