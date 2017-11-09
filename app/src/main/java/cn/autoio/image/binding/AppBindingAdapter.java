package cn.autoio.image.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import cn.autoio.image.R;
import cn.autoio.image.util.GlideManager;

/**
 * author: gavin
 * created on: 2017-11-01
 * description:
 */
public class AppBindingAdapter {

    @BindingAdapter(value = "imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView).load(url).apply(GlideManager.bigRoundOptions()).into(imageView);
    }

    @BindingAdapter(value = "checked")
    public static void setSelected(ImageView imageView, boolean isChecked) {
        imageView.setImageResource(isChecked ? R.drawable.ic_check_checked : R.drawable.ic_check_normal);
    }

    @BindingAdapter(value = "showing")
    public static void setShowing(View view, boolean isShowing) {
        view.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = "focused")
    public static void setFocused(ImageView imageView, boolean focused) {
        if (focused) {
            Animation animation = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.zoom_in);
            imageView.startAnimation(animation);
        } else {
            Animation animation = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.zoom_out);
            imageView.startAnimation(animation);
        }

    }


    /**
     * 加载为普通图片
     *
     * @param imageView imageView
     * @param url       url
     */
    @BindingAdapter(value = "normalImage")
    public static void loadNormalImage(ImageView imageView, String url) {
        Glide.with(imageView).load(url).into(imageView);
    }

    /**
     * 加载为小圆角图片
     *
     * @param imageView imageView
     * @param url       url
     */
    @BindingAdapter(value = "smallRoundImage")
    public static void loadSmallRoundImage(ImageView imageView, String url) {
        Glide.with(imageView).load(url).apply(GlideManager.smallRoundOptions()).into(imageView);
    }

    @BindingAdapter(value = "rotation")
    public static void setRotation(PhotoView photoView, int rotation) {
        photoView.setRotation(rotation);
    }
}
