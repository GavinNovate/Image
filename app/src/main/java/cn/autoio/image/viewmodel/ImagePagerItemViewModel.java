package cn.autoio.image.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import cn.autoio.image.base.BaseViewModel;
import cn.autoio.image.model.Image;

/**
 * author: gavin
 * created on: 2017-11-08
 * description:
 */
public class ImagePagerItemViewModel extends BaseViewModel {

    private MutableLiveData<Image> imageLiveData = new MutableLiveData<>();

    public ImagePagerItemViewModel(@NonNull Application application, LifecycleOwner owner, LiveData<List<Image>> imageListLiveData, int index) {
        super(application);

        imageListLiveData.observe(owner, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                if (images != null && index < images.size()) {
                    imageLiveData.postValue(images.get(index));
                }
            }
        });
    }

    public LiveData<Image> getImage() {
        return imageLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;
        private final LifecycleOwner owner;
        private final LiveData<List<Image>> imageListLiveData;
        private final int index;

        public Factory(Application application, LifecycleOwner owner, LiveData<List<Image>> imageListLiveData, int index) {
            this.application = application;
            this.owner = owner;
            this.imageListLiveData = imageListLiveData;
            this.index = index;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ImagePagerItemViewModel(application, owner, imageListLiveData, index);
        }
    }
}
