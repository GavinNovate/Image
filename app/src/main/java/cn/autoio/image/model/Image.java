package cn.autoio.image.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

/**
 * author: gavin
 * created on: 2017-10-27
 * description:图片实体
 */
public class Image implements Comparable<Image> {

    private String name;
    private String path;
    private String type;
    private long time;
    private ObservableBoolean checked = new ObservableBoolean();
    private ObservableInt rotation = new ObservableInt();

    public Image() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public long getTime() {
        return time;
    }


    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public ObservableBoolean getChecked() {
        return checked;
    }

    public void setChecked(ObservableBoolean checked) {
        this.checked = checked;
    }

    public void setRotation(int rotation) {
        this.rotation.set(rotation);
    }

    public ObservableInt getRotation() {
        return rotation;
    }

    public void setRotation(ObservableInt rotation) {
        this.rotation = rotation;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Image && ((Image) obj).path.equals(path);
    }

    /**
     * 按创建时间逆序排列
     *
     * @param target 目标图片
     * @return 排序指数
     */
    @Override
    public int compareTo(@NonNull Image target) {
        long sub = time - target.time;
        if (sub > 0) {
            return -1;
        } else if (sub < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
