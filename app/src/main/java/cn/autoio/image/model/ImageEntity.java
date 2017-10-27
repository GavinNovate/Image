package cn.autoio.image.model;

/**
 * author: gavin
 * created on: 2017-10-27
 * description:图片实体
 */
public class ImageEntity implements Image {

    private String name;

    public ImageEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
