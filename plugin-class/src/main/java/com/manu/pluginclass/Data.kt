package com.manu.pluginclass

import com.manu.plugin_library.IData
import com.manu.plugin_library.IDataCallback

/**
 * @Desc:
 * @Author: jzman
 */
class Data : IData {
    lateinit var callback: IDataCallback
    private var data: String = "default"

    override fun setData(data: String) {
        this.data = data;
    }

    override fun getData(): String {
        return data;
    }

    override fun registerCallback(callback: IDataCallback) {
        this.callback = callback
        click()
    }

    private fun click() {
        callback.setResult("data from plugin apk:$data")
    }
}