package cn.autoio.image.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;

import cn.autoio.image.base.BaseViewModel;
import cn.autoio.image.model.Image;
import cn.autoio.image.repository.ImageListRepository;

/**
 * author: gavin
 * created on: 2017-11-08
 * description:
 */
public class ImagePagerViewModel extends BaseViewModel {

    // -- 状态 --
    // 是否显示标题栏和导航栏
    private ObservableBoolean showState;
    // 改变以使得ViewPager选中对应的页面
    private MutableLiveData<Integer> pagerLiveData;
    // 当前选中的索引
    private MutableLiveData<Integer> indexLiveData;

    // -- 数据 --
    // 当前选中的图片
    private MutableLiveData<Image> imageLiveData;
    private ImageListRepository repository;

    public ImagePagerViewModel(@NonNull Application application) {
        super(application);

        showState = new ObservableBoolean();
        pagerLiveData = new MutableLiveData<>();
        indexLiveData = new MutableLiveData<>();

        repository = new ImageListRepository(getApp());
        imageLiveData = new MutableLiveData<>();
    }

    /**
     * 获取显示状态
     *
     * @return 显示状态
     */
    public ObservableBoolean getShowState() {
        return showState;
    }

    /**
     * 设置显示状态
     *
     * @param showState 是否显示标题栏和状态栏
     */
    public void setShowState(boolean showState) {
        this.showState.set(showState);
    }

    public LiveData<Image> getImage() {
        return imageLiveData;
    }

    public LiveData<Integer> getPager() {
        return pagerLiveData;
    }

    public LiveData<Integer> getIndex() {
        return indexLiveData;
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
     * 被选中的页面
     * ViewPager翻页时选中的界面
     *
     * @param index 索引
     */
    public void selected(int index) {
        List<Image> images = getImages().getValue();
        if (images != null && index < images.size()) {
            // 清除选中的图片
            for (int i = 0; i < images.size(); i++) {
                if (i != index && images.get(i).getChecked().get()) {
                    images.get(i).setChecked(false);
                }
            }

            // 选中当前图片
            if (!images.get(index).getChecked().get()) {
                images.get(index).setChecked(true);
            }
            indexLiveData.postValue(index);
            imageLiveData.postValue(images.get(index));
        }
    }

    /**
     * 选中图片地址所对应的页面
     *
     * @param path   需要恢复的图片的地址
     * @param images 图片列表
     */
    public void select(List<Image> images, String path) {
        if (images != null && path != null) {
            int index = -1;
            for (int i = 0; i < images.size(); i++) {
                if (path.equals(images.get(i).getPath())) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                pagerLiveData.postValue(index);
            }
        }
    }

    /**
     * 逆时针旋转90度
     */
    public void contraRotate() {
        if (imageLiveData.getValue() != null) {
            if (imageLiveData.getValue().getRotation().get() == 0) {
                imageLiveData.getValue().setRotation(270);
            } else {
                imageLiveData.getValue().setRotation(imageLiveData.getValue().getRotation().get() - 90);
            }
        }
    }

    /**
     * 删除选中图片
     */
    public void deleteImage(Image image) {
        repository.deleteImage(image);
    }
}
