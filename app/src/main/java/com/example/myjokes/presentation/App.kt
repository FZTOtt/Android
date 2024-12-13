package com.example.myjokes.presentation

import android.app.Application
import com.example.myjokes.data_clean.datasource.local.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
        println("init db")
    }
}