package cn.autoio.image.source;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.autoio.image.model.Image;
import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;

/**
 * author: gavin
 * created on: 2017-10-30
 * description:图片媒体库访问实现类
 */
public class ImageSource {
    private static final String TAG = "ImageSource";

    // 图片查询参数
    private static final String[] COLUMNS_IMAGE = new String[]{
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.DATE_ADDED,
    };


    private Context context;
    private ImageContentObserver contentObserver;
    private FlowableProcessor<List<Image>> imagesFlowable;

    public ImageSource(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 添加图片到媒体库
     *
     * @param images 图片列表
     */
    public void insertImages(@NonNull List<Image> images) {
        if (images.size() < 1) {
            return;
        }
        String[] paths = new String[images.size()];
        for (int i = 0; i < images.size(); i++) {
            paths[i] = images.get(i).getPath();
        }
        MediaScannerConnection.scanFile(context, paths, null, null);
    }

    /**
     * 从媒体库删除图片
     *
     * @param images 图片列表
     */
    public void deleteImages(@NonNull List<Image> images) {
        if (images.size() < 1) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i).getPath());
            if (file.exists()) {
                if (file.delete()) {
                    builder.append("'").append(images.get(i).getPath()).append("'");
                    if (i < images.size() - 1) {
                        builder.append(",");
                    }
                }
            }
        }
        builder.append(")");
        context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + " IN " + builder.toString(), null);
    }

    /**
     * 获取媒体库图片列表
     *
     * @return 图片列表
     */
    @NonNull
    public List<Image> selectImages() {
        List<Image> images = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, COLUMNS_IMAGE, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Image image = new Image();
                    image.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)));
                    image.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                    image.setType(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)));
                    image.setTime(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)));
                    images.add(image);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        Collections.sort(images);
        return images;
    }

    /**
     * 观察媒体库图片列表
     *
     * @return 观察对象
     */
    @NonNull
    public Flowable<List<Image>> observeImages() {
        if (imagesFlowable == null) {
            imagesFlowable = BehaviorProcessor.create();
            imagesFlowable.onNext(selectImages());
        }
        return imagesFlowable;
    }

    /**
     * 注册观察
     */
    public void registerObserver() {
        if (contentObserver == null) {
            contentObserver = new ImageContentObserver();
            context.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver);
        }
    }

    /**
     * 取消观察
     */
    public void unRegisterObserver() {
        if (contentObserver != null) {
            context.getContentResolver().unregisterContentObserver(contentObserver);
            contentObserver = null;
        }
    }

    private void onImagesChanged() {
        if (imagesFlowable != null && imagesFlowable.hasSubscribers() && !imagesFlowable.hasThrowable() && !imagesFlowable.hasComplete()) {
            imagesFlowable.onNext(selectImages());
        }
    }

    class ImageContentObserver extends ContentObserver {

        ImageContentObserver() {
            super(null);
        }

        @Override
        public void onChange(boolean selfChange) {
            onImagesChanged();
        }
    }
}
