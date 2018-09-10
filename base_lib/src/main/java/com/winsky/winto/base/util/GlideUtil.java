package com.winsky.winto.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * <br/>================================================
 * <br/> @author：Blizzard-liu
 * <br/> @date：2018/5/3 15:21
 * <br/> @describe：
 * <br/>================================================
 */
public class GlideUtil {



    ///////////////////////////////////////  具 体 方 法  ////////////////////////////////////////////

    /**
     * 默认glide，不做任何处理，glide 从字符串中加载图片（网络地址或者本地地址）
     */
    public static void intoDefault(Context context, String url, ImageView view) {
        Glide.with(context).load(url).into(view);
    }

    /**
     * 默认glide，不做任何处理，加载资源图片
     */
    public static void intoDefault(Context context, int id, ImageView view) {
        Glide.with(context).load(id).into(view);
    }

    private static RequestOptions getOptions(int defaultId) {
        return new RequestOptions()
                .placeholder(defaultId)// 正在加载中的图片
                .fitCenter()
                //                .error(R.drawable.video_error) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
    }
    private static RequestOptions getOptions() {
        return new RequestOptions()
                .centerCrop()
                //                .error(R.drawable.video_error) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
    }


    public static void into(Context context, Object url, ImageView view, int defaultId) {
        Glide.with(context).load(url)
                .apply(getOptions(defaultId))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(view);
    }

    public static void into(Context context, String url, ImageView view) {
        Glide.with(context).load(url)
                .apply(getOptions())
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(view);
    }



    /**
     * 恢复请求，一般在停止滚动的时候
     *
     * @param context
     */
    public static void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     *
     * @param context
     */
    public static void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 清理磁盘缓存
     *
     * @param mContext
     */
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    /**
     * 清理内存缓存
     *
     * @param mContext
     */
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }


    public static Bitmap getImageByPath(String path){
        File file = new File(path);
        Bitmap bm=null;

        if(file.exists()){
           bm = BitmapFactory.decodeFile(path);

        }
        return bm;
    }



    public static  Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
    private static  Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
