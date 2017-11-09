package cn.autoio.image.view.event;

import cn.autoio.image.model.Image;

/**
 * author: gavin
 * created on: 2017-11-09
 * description:
 */
public interface ImageTitleFragmentEvent {
    void onBack();

    void onDelete(Image image);
}
