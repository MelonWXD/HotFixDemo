package com.dongua.hotfixdemo.util;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * author: Lewis
 * data: On 18-1-24.
 */

public class HotFixUtil {


    /**
     *
     * @param appContext App的上下文
     * @param fixDexPath 热修复文件的位置
     */
    public static void inject(Context appContext, String fixDexPath) {

        try {

            Class<?> base = Class.forName("dalvik.system.BaseDexClassLoader");
            PathClassLoader pathClassLoader = (PathClassLoader) appContext.getClassLoader();
            //获取当前类加载器的pathList和dexElements
            Object pathList = getField(base, "pathList", pathClassLoader);
            Object dexElements = getField(pathList.getClass(), "dexElements", pathList);


            //加载FixDex文件
            String optPath = appContext.getDir("optimize", 0).getAbsolutePath();
            DexClassLoader myDexLoader = new DexClassLoader(fixDexPath, optPath, null, pathClassLoader);
            //获取fixDex文件的pathList和dexElements
            Object fixPathList = getField(base, "pathList", myDexLoader);
            Object fixDexElements = getField(fixPathList.getClass(), "dexElements", fixPathList);


            //合并fixDexElements和dexElements
            Object newDexElements = combineArray(fixDexElements, dexElements);

            //将修复好的newDexElements赋值给当前的类加载器
            setField(pathList.getClass(), "dexElements", pathList, newDexElements);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * @param cl    要获取的field的类
     * @param field 要获取的field的名字
     * @param obj   要获取的field的类的实例
     * @return 实例obj中的field域
     */
    private static Object getField(Class<?> cl, String field, Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    /**
     * @param cl    要获取的field的类
     * @param field 要获取的field的名字
     * @param obj   要赋值field域的实例
     * @param value 实例obj的field域的新值
     */
    private static void setField(Class<?> cl, String field, Object obj, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cl.getDeclaredField(field);
        declaredField.setAccessible(true);
        declaredField.set(obj, value);
    }


    private static Object combineArray(Object firstArr, Object secondArr) {
        int firstLength = Array.getLength(firstArr);
        int secondLength = Array.getLength(secondArr);
        int length = firstLength + secondLength;

        Class<?> componentType = firstArr.getClass().getComponentType();
        Object newArr = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            if (i < firstLength) {
                Array.set(newArr, i, Array.get(firstArr, i));
            } else {
                Array.set(newArr, i, Array.get(secondArr, i - firstLength));
            }
        }
        return newArr;
    }
}

