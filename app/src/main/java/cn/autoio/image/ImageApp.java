package cn.autoio.image;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.autoio.image.model.ImageEntity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * author: gavin
 * created on: 2017-10-27
 * description:相册应用
 */
public class ImageApp extends Application {

    private static final String TAG = "ImageApp";

    List<ImageEntity> imageList = new ArrayList<>();

    Disposable disposable;

    @Override
    public void onCreate() {
        super.onCreate();


        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        imageList.add(new ImageEntity().setName(aLong.toString()));
                        subject.onNext(imageList);
                        Log.d(TAG, "onNext: " + imageList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public Subject<List<ImageEntity>> subject = PublishSubject.create();

    public Observable<List<ImageEntity>> observeImageList() {
        return subject;
    }
}
