package com.manu.plugin_res

import android.content.Context
import com.manu.plugin_library.IPlugin

/**
 * @Desc: Plugin
 * @Author: jzman
 */
class Plugin : IPlugin{
    override fun getStringResId(context: Context): String {
        return context.getString(R.string.app_name)
    }
}