package cn.autoio.image.base;

import cn.autoio.image.ImageApp;

/**
 * author: gavin
 * created on: 2017-11-01
 * description:
 */
public class BaseRepository {

    private ImageApp app;

    public BaseRepository(ImageApp app) {
        this.app = app;
    }

    public ImageApp getApp() {
        return app;
    }
}
