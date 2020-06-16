package com.sysaxiom.garbagedriver.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sysaxiom.room.repository.LoginRepository
import com.sysaxiom.room.ui.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: LoginRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}