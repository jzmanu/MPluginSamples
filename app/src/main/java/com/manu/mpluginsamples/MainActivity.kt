package com.manu.mpluginsamples

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.manu.mpluginsamples.util.ReflectHelper
import com.manu.plugin_library.IData
import com.manu.plugin_library.IDataCallback
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        PluginManager.init(this)
        PluginManager.downLoadPlugin("plugin-debug.apk", object : PluginManager.DownLoadCallback {
            override fun downLoadSuccess() {
                Log.i(TAG, "downLoadSuccess")
                val file = getFileStreamPath("plugin-debug.apk")
                PluginManager.initDexClassLoader(file.absolutePath)
                PluginManager.applicationInit()
            }

            override fun downLoadFail(error: String) {
                Log.i(TAG, "downLoadFail > error:$error")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("getCodeCacheDir:$codeCacheDir")
        println("getCacheDir:$cacheDir")
        println("externalCacheDir:$externalCacheDir")

    }

    fun loadPlugin(view: View) {
        Log.i(TAG, "test")
        val className = "com.manu.plugin.Data"
        val pluginClass = PluginManager.mDexClassLoader.loadClass(className)
        val obj = ReflectHelper.createObject(pluginClass)
        val data: IData = obj as IData
        data.setData("躬行之")
        Log.i(TAG, "test > name:${data.getData()}")
        val callBack = object : IDataCallback {
            override fun setResult(value: String) {
                Log.i(TAG, "test > callBack : $value")
            }
        }
        data.registerCallback(callBack)
        getPluginName(getFileStreamPath("plugin-debug.apk"))
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    private fun getPluginName(apkFile: File): String? {
        val packageParse = ReflectHelper.createObject("android.content.pm.PackageParser")
        val paramTypes = arrayOf(
            File::class.java,
            Int::class.javaPrimitiveType
        )
        val paramValues =
            arrayOf(apkFile, PackageManager.GET_RECEIVERS)
        val method = ReflectHelper.invokeInstanceMethod(packageParse, "parsePackage", paramTypes, paramValues)
        val obj = ReflectHelper.getFieldObject("applicationInfo", method)
        val applicationInfo = (obj as ApplicationInfo)
        // TODO 待资源的插件化实现
//        val pm = context.packageManager
//        val pluginName = pm.getApplicationLabel(applicationInfo)
        return applicationInfo.className
    }
}