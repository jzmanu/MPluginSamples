package com.manu.plugin_library

/**
 * @Desc: 基础接口
 * @Author: jzman
 */
interface IData {
    fun setData(data: String)
    fun getData(): String
    fun registerCallback(callback: IDataCallback)
}