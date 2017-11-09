package cn.autoio.image.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.autoio.image.ImageApp;
import cn.autoio.image.base.BaseRepository;
import cn.autoio.image.model.Image;
import cn.autoio.image.source.ImageSource;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author: gavin
 * created on: 2017-10-30
 * description:图片列表资源库
 */
public class ImageListRepository extends BaseRepository {

    private static final String TAG = "ImageListRepository";

    // 图片源
    private ImageSource imageSource;
    private MutableLiveData<List<Image>> imageListLiveData = new MutableLiveData<>();

    public ImageListRepository(ImageApp app) {
        super(app);
        init();
    }

    /**
     * 初始化资源
     */
    private void init() {
        imageSource = new ImageSource(getApp());
        imageSource.registerObserver();
        imageSource
                .observeImages()
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Image>>() {
                    @Override
                    public void accept(List<Image> images) throws Exception {
                        Log.d(TAG, "accept: postValue");
                        imageListLiveData.postValue(images);
                    }
                });
    }


    public LiveData<List<Image>> getImages() {
        return imageListLiveData;
    }


    /**
     * 删除单个图片
     *
     * @param image 图片
     */
    public void deleteImage(Image image) {
        if (image != null) {
            List<Image> images = new ArrayList<>();
            images.add(image);
            imageSource.deleteImages(images);
        }
    }

    /**
     * 删除所有被选中的图片
     */
    public void deleteImages() {
        if (imageListLiveData.getValue() != null) {
            List<Image> checkedImages = new ArrayList<>();
            for (Image image : imageListLiveData.getValue()) {
                if (image.getChecked().get()) {
                    checkedImages.add(image);
                }
            }
            imageSource.deleteImages(checkedImages);
        }
    }

    /**
     * 清理资源
     */
    public void clear() {
        imageSource.unRegisterObserver();
    }
}
