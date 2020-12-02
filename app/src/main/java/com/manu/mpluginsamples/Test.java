package com.manu.mpluginsamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

public class Test extends AppCompatActivity {
    private AssetManager mAssetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                PluginManager.applicationInit()
            }
        });

    }

    public static void test(Context context){
        Intent intent = new Intent(context,PluginClassActivity.class);
        context.startActivity(intent);
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager == null ? super.getAssets() : mAssetManager;
    }
}