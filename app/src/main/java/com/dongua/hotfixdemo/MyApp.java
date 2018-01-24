package com.dongua.hotfixdemo;

import android.app.Application;
import android.util.Log;

import com.dongua.hotfixdemo.util.HotFixUtil;
import com.dongua.hotfixdemo.util.SharedPreferenceUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * author: Lewis
 * data: On 18-1-18.
 */

public class MyApp extends Application {
    public static final String SP_KEY_HOTFIX = "hotfix_key";
    @Override
    public void onCreate() {

        super.onCreate();

        Boolean isHotFixOn = (Boolean) SharedPreferenceUtil.get(this,SP_KEY_HOTFIX,false);
        if(isHotFixOn)
            HotFixUtil.inject(this,"/data/local/tmp/fix/fix.dex");

    }



}
