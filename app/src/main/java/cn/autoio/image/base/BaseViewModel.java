package cn.autoio.image.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import cn.autoio.image.ImageApp;

/**
 * author: gavin
 * created on: 2017-11-01
 * description:
 */
public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public ImageApp getApp() {
        return getApplication();
    }
}
