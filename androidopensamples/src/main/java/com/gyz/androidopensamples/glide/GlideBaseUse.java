package com.gyz.androidopensamples.glide;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.gyz.androidopensamples.R;
import com.gyz.androidopensamples.glide.modelloader.CustomDataModel;
import com.gyz.androidopensamples.glide.modelloader.CustomModelLoader;
import com.gyz.androidopensamples.glide.signature.IntegerVersionSignature;
import com.gyz.androidopensamples.glide.transformations.BlurTransformation;
import com.gyz.androidopensamples.glide.transformations.CropCircleTransformation;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static android.R.attr.fragment;
import static android.provider.MediaStore.Images.ImageColumns.ORIENTATION;

/**
 * Created by guoyizhe on 2016/11/15.
 * 邮箱:gyzboy@126.com
 */

//glide中设计上的一些优点:
//1.Engine中getReferenceQueue中activeResource是一个持有WeakReference的Map集合,ReferenceQueue 就是提供资源 WeakReference 的虚引用队列。
// activeResources.put(key, new ResourceWeakReference(key, cached, getReferenceQueue()));
//这里要提的是负责清除 WeakReference 被回收的 activeResources 资源的实现：使用到了 MessageQueue.IdleHandler，
// 源码的注释：当一个线程等待更多 message 的时候会触发该回调,就是 messageQuene 空闲的时候会触发该回调，里面还有一个queueIdle方法负责清除WeakReference被回收的资源

    //2.生命周期管理:
    //glide为当前的上下文 Activity 或者 Fragment 绑定一个 TAG 为"com.bumptech.glide.manager"的 RequestManagerFragment，然后把该 fragment 作为 rootRequestManagerFragment，并加入到当前上下文的 FragmentTransaction 事务中，
// 从而与当前上下文 Activity 或者 Fragment 的生命周期保持一致。
public class GlideBaseUse extends Activity {

    ImageView imageView;
    private String picUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide);

        imageView = (ImageView) findViewById(R.id.iv_image);
        picUrl = "http://pic.pp3.cn/uploads//201510/2015101803.jpg";
//        Glide.with(this)//传入Activity或者Fragment与其生命周期相同
//                .load(R.mipmap.ic_launcher)
//                .placeholder(R.mipmap.big) //设置占位图
//                .error(R.mipmap.icon) //设置错误图片
//                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
//                //.dontAnimate() //不显示动画效果
//                .priority(Priority.NORMAL)//4个级别
//                .override(200, 200)//现在图片大小
////                .centerCrop()
//                .listener(new RequestListener<Integer, GlideDrawable>() {//可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘
//                    @Override
//                    public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .thumbnail(0.1f)
//                .skipMemoryCache(true)//跳过内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//设置磁盘缓存
//                .into(imageView);

        download();
//        loadFromCache();
        loadWithCustomTransformation();
//        loadWithCustomSignatureKey();
    }

    /**
     * 加载资源
     *
     * @param imageView
     */
    private void loadPic(ImageView imageView) {

//从其他源加载图片(资源文件、文件、Uri、assets、raw、ContentProvider、sd卡资源)
//        Glide.with(this).load(file).into(imageView);
//        Uri uri = resourceIdToUri(this, R.mipmap.ic_launcher);
//        Glide.with(this).load("Android.resource://com.frank.glide/raw/raw_1").into(imageView);
//        Glide.with(this).load("android.resource://com.frank.glide/raw/"+R.raw.raw_1).into(imageView);
//        Glide.with(this).load("content://media/external/images/media/139469").into(imageView);
//        Glide.with(this).load("file:///android_asset/f003.gif").into(imageView);
//        Glide.with(this).load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg").into(imageView);
    }


    /**
     * 清除缓存
     */
    private void cleanCache() {
        Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
        Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
        Glide.clear(imageView);//清除所有图片加载请求

    }

    /**
     * 加载不同类型图片
     */
    private void loadDifPic() {
//        Glide.with(this).load(gifUrl).asBitmap().into(imageView); //显示gif静态图片
//        Glide.with(this).load(gifUrl).asGif().into(imageView); //显示gif动态图片
    }

    /**
     * 转码
     *
     * @param resource
     */
    private void transcode(final Drawable resource) {
        Glide.with(this)
                .load(picUrl)
                .asBitmap()
                .toBytes()
                .centerCrop()
                .into(new SimpleTarget<byte[]>(250, 250) {
                    @Override
                    public void onResourceReady(byte[] data, GlideAnimation anim) {
                        //将转码后的250*250图片进行上传
                        //uploadPic
                    }
                });
    }

    /**
     * 加载图片带动画
     *
     * @param url
     */
    private void loadWithAnim(String url) {
//        Glide.with(this)
//                .load(url)
//                .animate(android.R.anim.slide_in_left)
//                .into(imageView);


        ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(2500);
                fadeAnim.start();
            }
        };
        Glide.with(this)
                .load(url)
                .animate(animationObject)
                .into(imageView);
    }

    /**
     * 下载图片到cache中
     */
    private void download() {
        new Thread(new Runnable() {//需要在异步线程中进行
            @Override
            public void run() {
                FutureTarget<File> future = Glide.with(getApplicationContext())
                        .load(picUrl)
                        .downloadOnly(500, 500);
                File cacheFile = null;
                try {
                    cacheFile = future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println(cacheFile.getAbsolutePath());
            }
        }).start();
    }

    /**
     * 从cache中加载资源
     */
    private void loadFromCache() {
        Glide.with(this)
                .load(picUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//or DiskCacheStrategy.SOURCE可以保证程序会去读取缓存文件
                .into(imageView);
    }

    /**
     * 获得bitmap
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private Bitmap getBitmap() throws ExecutionException, InterruptedException {
        return Glide.with(this)
                .load(picUrl)
                .asBitmap()
                .centerCrop()
                .into(500, 500)
                .get();
    }

    /**
     * 加载图片自定义图片效果
     */
    private void loadWithCustomTransformation() {
        Glide.with(this).load(picUrl).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new BlurTransformation(this, 25), new CropCircleTransformation(this)).into(imageView);
    }

    /**
     * 自定义签名key
     */
    private void loadWithCustomSignatureKey() {
        //Glide当中图片缓存key的生成是通过一个散列算法来实现的，所以很难手动去确定哪些文件可以从缓存当中进行删除，
        // 通常的做法就是当内容（url，file path）改变的时候，改变相应的标识符就可以了，通常标识符的改变可能很困难，
        // Glide当中也提供了signature()方法，可以将一个附加的数据加入到缓存key当中

//        —-多媒体存储数据，可以使用MediaStoreSignature类作为标识符，会将文件的修改时间、mimeType等信息作为cacheKey的一部分
//        —-文件，可以使用StringSignature
//        —-Urls ,可以使用StringSignature

        Glide.with(this)
                .load(picUrl)
//                .signature(new MediaStoreSignature(MediaStore.Images.ImageColumns.MIME_TYPE, Long.valueOf(MediaStore.Images.ImageColumns.DATE_MODIFIED), Integer.valueOf(ORIENTATION)))
                .signature(new IntegerVersionSignature(2))
                .into(imageView);
    }

    private void loadWithCustomModelLoader() {
        Glide.with(this).using(new CustomModelLoader(this)).load(new CustomDataModel() {
            @Override
            public String buildUrl(int width, int height) {
                return picUrl;
            }
        }).into(imageView);
    }

    private void loadWithCustomModelLoaderWithourUsing() {
        Glide.with(this).load(new CustomDataModel() {
            @Override
            public String buildUrl(int width, int height) {
                return picUrl;
            }
        }).into(imageView);
    }
}
