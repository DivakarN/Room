package com.sysaxiom.room.database

fun mockLoginResponse(): LoginResponse {
    val userDataList = listOf(
        UserData("One","Divakar"),
        UserData("Two","Nandagopal"),
        UserData("Three","N")
    )

    val loginData = LoginData(userDataList)
    return LoginResponse(true,1,loginData)
}