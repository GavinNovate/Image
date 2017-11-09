package cn.autoio.image.view.event;

import cn.autoio.image.model.Image;

/**
 * author: gavin
 * created on: 2017-11-09
 * description:
 */
public interface ImageNavigationItemEvent {
    void onCheck(Image image);
}
