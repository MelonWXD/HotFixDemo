package com.dongua.hotfixdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import java.util.HashSet;
import java.util.Set;

/**
 * author: Lewis
 * data: On 18-1-24.
 */

public class SharedPreferenceUtil {

    public static void put(Context context, String key, Object value) {
        String type = value.getClass().getSimpleName();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) value);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) value);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) value);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) value);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) value);
        } else if ("Set".equals(type)) {
            editor.putStringSet(key, (Set) value);
        } else if ("HashSet".equals(type)) {
            editor.putStringSet(key, (HashSet) value);
        }
        editor.commit();
    }

    public static Object get(Context context, String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        } else if ("Set".equals(type)) {
            return sharedPreferences.getStringSet(key, (Set) defValue);
        } else if ("HashSet".equals(type)) {
            return sharedPreferences.getStringSet(key, (Set) defValue);
        }
        return null;
    }
}