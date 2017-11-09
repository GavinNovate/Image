package cn.autoio.image.view.event;

import cn.autoio.image.model.Image;

/**
 * author: gavin
 * created on: 2017-11-06
 * description:图片列表中每个图片的点击事件
 */
public interface ImageListItemEvent {
    void onClick(Image image);

    boolean onLongClick(Image image);
}
