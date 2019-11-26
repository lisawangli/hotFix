package com.example.wangli.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class FixDexUtils {
    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        //每次修复之前都清空，然后再重新赋值
        loadedDex.clear();
    }

    //loadedDex 赋值（筛选，私有目录下面有很多文件）
    public static void loadFixedDex(Context context){
        File fileDir = context.getDir(Constants.DEX_DIR,Context.MODE_PRIVATE);
        File[] listFiles = fileDir.listFiles();
        //循环私有目录下的所有的文件
        for (File file : listFiles) {
            if (file.getName().endsWith(Constants.DEX_SUFFIX)&&!"classes.dex".equalsIgnoreCase(file.getName())){
                loadedDex.add(file);
            }
        }
        createDexClassLoader(context,fileDir);
    }

    private static void createDexClassLoader(Context context, File fileDir) {
        //创建解压目录
        String optimizedDir = fileDir.getAbsolutePath()+File.separator+"opt_dex";
        File fopt = new File(optimizedDir);
        if (!fopt.exists()){
            fopt.mkdirs();
        }
        Log.e("FileDexUtils","fopt===="+fopt.getAbsolutePath());
        for (File dex : loadedDex) {
            //用自己创建的类加载器来加载自己的修复包 dex
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(),optimizedDir,null,context.getClassLoader());
            hotfix(classLoader,context);
        }
    }

    private static void hotfix(DexClassLoader classLoader, Context context) {
        //获取系统的pathClassLoader（系统类加载器）
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
        try {
            //获取自己的dexElements，获取dexElements之前必须要先获取pathList
            Object myElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(classLoader));
            //获取系统的dexElements，获取dexElements之前必须要先获取pathList
            Object systemElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(pathClassLoader));
            //合并两个数组成为一个新的数组，并将自己的数组放在新的数组前面
            Object newElements = ArrayUtils.combineArray(myElements,systemElements);
            //通过反射获取系统的pathList属性
            Object systemPathlist = ReflectUtils.getPathList(pathClassLoader);
            //通过反射将新的数组newDexElements[]赋值给pathList属性
            ReflectUtils.setField(systemPathlist,systemPathlist.getClass(),newElements);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
