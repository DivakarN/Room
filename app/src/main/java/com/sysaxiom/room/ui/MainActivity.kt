package com.sysaxiom.room.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.sysaxiom.garbagedriver.ui.login.MainViewModelFactory
import com.sysaxiom.room.R
import com.sysaxiom.room.database.mockLoginResponse
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()

    private val factory : MainViewModelFactory by instance()

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        view_data.setOnClickListener {
            viewModel.getLoginResponse(this)
        }

        insert_data.setOnClickListener {
            viewModel.upsertLoginResponse(mockLoginResponse())
        }

        delete_data.setOnClickListener {
            viewModel.deleteLoginResponse()
        }

    }
}
