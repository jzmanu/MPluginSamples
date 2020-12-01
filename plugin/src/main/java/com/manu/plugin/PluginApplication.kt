package com.manu.plugin

import android.app.Application
import android.util.Log

/**
 * @Desc: PluginLibApplication
 * @Author: jzman
 */
class PluginApplication: Application() {
    companion object{
        private const val TAG = "PluginApplication"
    }
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG,"onCreate")
    }
}