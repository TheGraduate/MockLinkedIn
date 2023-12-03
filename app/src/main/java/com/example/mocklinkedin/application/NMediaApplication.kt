package com.example.mocklinkedin.application

import android.app.Application
import com.example.mocklinkedin.auth.AppAuth

class NMediaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppAuth.initApp(this)
    }
}