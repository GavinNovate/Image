package cn.autoio.image.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import java.util.List;

import cn.autoio.image.base.BaseViewModel;
import cn.autoio.image.model.Image;
import cn.autoio.image.repository.ImageListRepository;

/**
 * author: gavin
 * created on: 2017-10-27
 * description:图片列表视图模型
 *
 * -- 状态 --
 * 1.公布当前模式
 * 2.公布当前选中图片数量
 *
 * -- 数据 --
 * 1.公布图片列表
 *
 * -- 功能 --
 * 1.切换模式 选择模式/普通模式
 * 2.选择图片 选中/反选某张图片
 * 3.反选所有图片
 * 4.删除图片 删除选中的图片
 */
public class ImageListViewModel extends BaseViewModel {

    // -- 状态 --
    // 是否为选择模式
    private ObservableBoolean checkState;
    // 选择的数量
    private ObservableInt checkedCount;

    // -- 数据 --
    private ImageListRepository repository;

    public ImageListViewModel(@NonNull Application application) {
        super(application);
        checkState = new ObservableBoolean();
        checkedCount = new ObservableInt();
        repository = new ImageListRepository(getApp());

        updateChecked();
    }

    /**
     * 公布当前模式
     *
     * @return 当前模式 true:选择模式 false:普通模式
     */
    public ObservableBoolean getCheckState() {
        return checkState;
    }

    /**
     * 公布当前选中图片数量
     *
     * @return 当前选中图片数量
     */
    public ObservableInt getCheckedCount() {
        return checkedCount;
    }

    /**
     * 公布图片列表
     *
     * @return 图片列表
     */
    public LiveData<List<Image>> getImages() {
        return repository.getImages();
    }

    /**
     * 切换模式
     *
     * @param checkState true:选择模式 false:普通模式
     */
    public void setCheckState(boolean checkState) {
        this.checkState.set(checkState);
    }

    /**
     * 选择/反选图片
     *
     * @param image 图片
     * @param check true:选择 false:反选
     */
    public void checkImage(Image image, boolean check) {
        if (getImages().getValue() != null) {
            if (getImages().getValue().contains(image)) {
                image.setChecked(check);
                updateChecked();
            }
        }
    }

    /**
     * 清除使用图片选中状态
     */
    public void clearChecked() {
        if (getImages().getValue() != null) {
            for (Image image : getImages().getValue()) {
                image.setChecked(false);
            }
        }
        updateChecked();
    }

    /**
     * 删除选中图片
     */
    public void deleteImages() {
        repository.deleteImages();
        updateChecked();
    }

    /**
     * 更新已选择的数量
     */
    private void updateChecked() {
        int count = 0;
        if (getImages().getValue() != null) {
            for (Image image : getImages().getValue()) {
                if (image.getChecked().get()) {
                    count++;
                }
            }
        }
        checkedCount.set(count);
    }

    /**
     * ViewModel被清理的时候，调用Repository中的清理方法主动清理资源
     */
    @Override
    protected void onCleared() {
        repository.clear();
    }
}
