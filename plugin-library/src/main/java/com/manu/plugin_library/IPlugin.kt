package com.manu.plugin_library

import android.content.Context

/**
 * @Desc: 基础插件接口
 * @Author: jzman
 */
interface IPlugin {
    fun getStringResId(context: Context):String
}