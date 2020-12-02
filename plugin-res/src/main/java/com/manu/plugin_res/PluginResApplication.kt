package com.manu.plugin_res

import android.app.Application
import android.util.Log

/**
 * @Desc: PluginResApplication
 * @Author: jzman
 */
class PluginResApplication : Application() {
    companion object{
        private const val TAG = "PluginResApplication"
    }
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG,"onCreate")
    }
}