package cn.autoio.image.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import cn.autoio.image.ImageApp;
import cn.autoio.image.model.ImageEntity;
import io.reactivex.functions.Consumer;

/**
 * author: gavin
 * created on: 2017-10-27
 * description:图片列表视图模型
 */
public class ImageListViewModel extends AndroidViewModel {

    private final MutableLiveData<List<ImageEntity>> observableImageList = new MutableLiveData<>();

    public ImageListViewModel(@NonNull Application application) {
        super(application);
        getApp().observeImageList().subscribe(new Consumer<List<ImageEntity>>() {
            @Override
            public void accept(List<ImageEntity> imageEntities) throws Exception {
                observableImageList.postValue(imageEntities);
            }
        });
    }


    public LiveData<List<ImageEntity>> getImageList() {
        return observableImageList;
    }

    @NonNull
    private ImageApp getApp() {
        return getApplication();
    }
}
