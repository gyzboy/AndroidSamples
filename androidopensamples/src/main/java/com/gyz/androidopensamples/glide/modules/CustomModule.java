package com.gyz.androidopensamples.glide.modules;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.module.GlideModule;
import com.gyz.androidopensamples.glide.modelloader.CustomDataModel;
import com.gyz.androidopensamples.glide.modelloader.CustomModelLoader;

import java.io.File;
import java.io.InputStream;

/**
 * Created by guoyizhe on 2016/11/17.
 * 邮箱:gyzboy@126.com
 */

public class CustomModule implements GlideModule {

    //GlideModule 是一个抽象方法，全局改变 Glide 行为的一个方式，通过全局GlideModule 配置Glide，用GlideBuilder设置选项，
    // 用Glide注册ModelLoader等,GlideModule 需要在AndroidManifest中进行注册


    //全局配置
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //GlideBuilder是glide的设置选项,包括内存设置,缓存设置等
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//默认加载的是RGB_565,使用内存是ARGB_8888的一半,但不支持透明度

        int maxMemory = (int) Runtime.getRuntime().maxMemory();//系统分配给应用的总内存大小
        builder.setMemoryCache(new LruResourceCache(maxMemory / 8));

        //设置glide磁盘缓存大小
        File cacheDir = context.getExternalCacheDir();//指定的是数据的缓存地址
        int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
        //设置磁盘缓存大小
        builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));


        //存放在data/data/xxxx/cache/
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
        //存放在外置文件浏览器
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize));

        //设置BitmapPool缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(maxMemory));

    }

    //用来在Glide单例创建之后但请求发起之前注册组件，该方法每次实现只会被调用一次。通常在该方法中注册ModelLoader。
    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(CustomDataModel.class, InputStream.class,
                new CustomModelLoader.Factory());
    }

    /**
     * 获取默认的内存使用计算函数
     * @param context
     */
    private void getDefaultMemoryUse(Context context){
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
    }
}
