package cn.autoio.image.util;

import com.bumptech.glide.request.RequestOptions;

/**
 * author: gavin
 * created on: 2017-11-03
 * description:
 */
public class GlideManager {

    private static RequestOptions bigRoundOptions;
    private static RequestOptions smallRoundOptions;

    public static RequestOptions bigRoundOptions() {
        if (bigRoundOptions == null) {
            bigRoundOptions =
                    new RequestOptions()
                            .transform(new GlideRoundTransform(16));
        }
        return bigRoundOptions;
    }

    public static RequestOptions smallRoundOptions() {
        if (smallRoundOptions == null) {
            smallRoundOptions =
                    new RequestOptions()
                            .transform(new GlideRoundTransform(4));
        }
        return smallRoundOptions;
    }
}
