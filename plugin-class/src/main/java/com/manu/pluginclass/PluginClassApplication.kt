package com.manu.pluginclass

import android.app.Application
import android.util.Log

/**
 * @Desc: PluginLibApplication
 * @Author: jzman
 */
class PluginClassApplication: Application() {
    companion object{
        private const val TAG = "PluginClassApplication"
    }
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG,"onCreate")
    }
}