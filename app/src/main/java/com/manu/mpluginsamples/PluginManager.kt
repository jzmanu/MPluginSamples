package com.manu.mpluginsamples

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import com.manu.mpluginsamples.util.ReflectHelper
import dalvik.system.DexClassLoader
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


/**
 * @Desc: 插件管理器
 * @Author: jzman
 */
object PluginManager{
    private const val TAG = "PluginManager"
    private lateinit var mContext:Context
    lateinit var mDexClassLoader: DexClassLoader

    /**
     * 初始化插件的Context
     */
    fun init(context: Context){
        Log.i(TAG, "init")
        mContext = context.applicationContext
    }

    /**
     * 初始化DexClassLoader
     */
    fun initDexClassLoader(apkPath: String){
        Log.i(TAG, "initDexClassLoader > apkPath:$apkPath")
        mDexClassLoader = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            DexClassLoader(apkPath, null, null, mContext.classLoader)
        }else{
            DexClassLoader(
                apkPath,
                mContext.codeCacheDir.absolutePath,
                null,
                mContext.classLoader
            )
        }
    }

    /**
     * 插件Application的初始化
     * 可在插件下载完毕后反射调用插件Apk的Application的onCreate方法实现其初始化
     */
    fun applicationInit(){
        Log.i(TAG, "applicationInit")
        val className = "com.manu.plugin.PluginApplication"
        val pluginClass = mDexClassLoader.loadClass(className)
        val obj = ReflectHelper.createObject(pluginClass)
        val application: Application = obj as Application
//        application.onCreate()
        val method = ReflectHelper.invokeInstanceMethod(application,"getApplicationInfo")


        Log.i(TAG, "applicationInit > className:${method}")
    }


    /**
     * 下载插件
     */
    fun downLoadPlugin(apkName: String, callback: DownLoadCallback) {
        Thread(DownRunnable(apkName,callback)).start()
    }

    /**
     * 从asset下载文件到缓存
     */
    private fun downLoadApkFromAssets(apkName:String,callback:DownLoadCallback){
        Log.i(TAG, "downLoadApkFromAssets > apkName:$apkName")
        val fileDir = mContext.filesDir
        if (!fileDir.exists()) fileDir.mkdirs()
        val apkFile = File(fileDir,apkName)
        if (apkFile.exists()){
            apkFile.delete()
        }
        try {
            if (!apkFile.exists()){
                val ins = mContext.assets.open(apkName)
                val fos = FileOutputStream(apkFile)
                val bytes = ByteArray(ins.available())
                Log.i(TAG, "downLoadApkFromAssets > available:${ins.available()}")
                var byteCount = 0
                while (byteCount != -1){
                    byteCount = ins.read(bytes)
                    if (byteCount == -1) break
                    Log.i(TAG, "downLoadApkFromAssets > byteCount:$byteCount")
                    fos.write(bytes,0,byteCount)
                }
                fos.flush()
                ins.close()
                fos.close()
                callback.downLoadSuccess()
            }else{
                callback.downLoadSuccess()
                Log.i(TAG, "downLoadApkFromAssets > 插件Apk已经存在")
            }
        }catch (e:Exception){
            e.message?.let { callback.downLoadFail(it) }
            e.printStackTrace()
        }
    }


    class DownRunnable(var apkName:String,var callback:DownLoadCallback) : Runnable{
        override fun run() {
            downLoadApkFromAssets(apkName,callback)
        }
    }

    interface DownLoadCallback{
        fun downLoadSuccess()
        fun downLoadFail(error:String)
    }
}