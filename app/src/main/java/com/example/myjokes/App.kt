package com.example.myjokes

import android.app.Application
import com.example.myjokes.data.db.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
        println("init db")
    }
}