package com.gyz.androidopensamples.glide.modelloader;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * Created by guoyizhe on 2016/11/17.
 * 邮箱:gyzboy@126.com
 */


public class CustomModelLoader extends BaseGlideUrlLoader<CustomDataModel> {
    public CustomModelLoader(Context context) {
        super(context);
    }



    @Override
    protected String getUrl(CustomDataModel model, int width, int height) {
        return model.buildUrl(width,height);
    }

    public static class Factory implements ModelLoaderFactory<CustomDataModel, InputStream> {
        private final ModelCache<CustomDataModel, GlideUrl> modelCache = new ModelCache<CustomDataModel, GlideUrl>(500);



        @Override
        public ModelLoader<CustomDataModel, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new CustomModelLoader(context);
        }

        @Override
        public void teardown() {
        }
    }
}
