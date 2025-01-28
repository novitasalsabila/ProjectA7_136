package com.example.klinik

import android.app.Application
import com.example.klinik.repository.AppContainer
import com.example.klinik.repository.KlinikContainer

class KlinikApplications : Application() {
    lateinit var  container : AppContainer
    override fun  onCreate () {
        super . onCreate ()
        container =  KlinikContainer ()
    }
}