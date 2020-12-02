package com.manu.mpluginsamples

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.manu.mpluginsamples.util.PluginManager
import com.manu.mpluginsamples.util.ReflectHelper
import com.manu.plugin_library.IPlugin

class PluginResActivity : AppCompatActivity() {

    private var mAssetManager: AssetManager ?= null
    private var mResources: Resources  ?= null
    private var mTheme: Resources.Theme  ?= null

    companion object {
        private const val TAG = "PluginResActivity"

        fun startPluginResActivity(context: Context) {
            val intent = Intent(context, PluginResActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        PluginManager.init(this)
        PluginManager.downLoadPlugin("plugin-res-debug.apk", object : PluginManager.DownLoadCallback {
            override fun downLoadSuccess() {
                Log.i(TAG, "downLoadSuccess")
                val file = getFileStreamPath("plugin-res-debug.apk")
                PluginManager.initDexClassLoader(file.absolutePath)
                runOnUiThread {
                    PluginManager.applicationInit("com.manu.plugin_res.PluginResApplication")
                    loadResource(getFileStreamPath("plugin-res-debug.apk").absolutePath)
                }

            }

            override fun downLoadFail(error: String) {
                Log.i(TAG, "downLoadFail > error:$error")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin_res)
    }

    override fun getAssets(): AssetManager? {
        return mAssetManager ?: super.getAssets()
    }

    override fun getResources(): Resources {
        return mResources ?: super.getResources()
    }

    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        return super.createConfigurationContext(overrideConfiguration)
    }

    override fun getTheme(): Resources.Theme {
        return mTheme ?: super.getTheme()
    }

    private fun loadResource(dexPath:String){
        Log.i(TAG,"loadResource > dexPath:$dexPath")
        mAssetManager = AssetManager::class.java.newInstance()
//        val paramTypes = arrayOf(
//            Array::class.java,
//            Boolean::class.javaPrimitiveType
//        )
//        val paramValues = arrayOf(
//            dexPath, false
//        )
//        ReflectHelper.invokeInstanceMethod(mAssetManager,"setApkAssets",paramTypes,paramValues)
        ReflectHelper.invokeInstanceMethod(mAssetManager,"addAssetPath", String::class.java,dexPath)
        mResources = Resources(mAssetManager,super.getResources().displayMetrics,super.getResources().configuration)
        mTheme?.setTo(super.getTheme())
    }

    fun getResourceData(view: View) {
        val className = "com.manu.plugin_res.Plugin"
        val pluginClass = PluginManager.mDexClassLoader.loadClass(className)
        val plugin = ReflectHelper.createObject(pluginClass) as IPlugin
//        val value = ReflectHelper.invokeInstanceMethod(plugin,"getStringResId",Context::class.java,this)
        Toast.makeText(this,"${plugin.getStringResId(this)}",Toast.LENGTH_LONG).show()
    }

}