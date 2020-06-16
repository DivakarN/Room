package com.sysaxiom.room.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sysaxiom.room.database.LoginResponse
import com.sysaxiom.room.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainViewModel(
    private val repository: LoginRepository
) : ViewModel(){

    fun upsertLoginResponse(loginResponse: LoginResponse) = GlobalScope.async(Dispatchers.IO) {
        repository.upsertLoginResponse(loginResponse)
    }

    fun deleteLoginResponse() = GlobalScope.async(Dispatchers.IO) {
        repository.deleteLoginResponse()
    }

    fun getLoginResponse(context: Context) = GlobalScope.async (Dispatchers.IO ) {
        repository.getLoginResponse()
            .subscribe({ loginResponseList ->
                if (loginResponseList != null && loginResponseList.isNotEmpty()) {
                    println(loginResponseList[0].toString())
                } else {
                    println("Insert Something")
                }
            }, {
                it.printStackTrace()
            })
    }

}