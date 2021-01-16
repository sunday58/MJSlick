package com.gwc

import android.app.Application

class ApplicationContext : Application() {
    private var instance: ApplicationContext? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

}