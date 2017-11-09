package cn.autoio.image.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

/**
 * author: gavin
 * created on: 2017-11-03
 * description:
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static final String TAG = "GlideRoundTransform";

    private float radius = 0f;

    public GlideRoundTransform(int dp) {
        radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        // 注意此处，roundCrop()中的第二个参数是调用centerCrop方法切好的Bitmap
        return roundCrop(pool, TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight));
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        Log.d(TAG, "roundCrop: " + source.getWidth() + ":" + source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

}
