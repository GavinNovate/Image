package cn.autoio.image.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.autoio.image.R;
import cn.autoio.image.databinding.ImageItemBinding;
import cn.autoio.image.model.Image;

/**
 * author: gavin
 * created on: 2017-10-27
 * description:图片列表适配器
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private static final String TAG = "ImageAdapter";

    private List<? extends Image> imageList;

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.image_item, parent,
                false
        );
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.binding.setImage(imageList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return imageList == null ? 0 : imageList.size();
    }

    /**
     * 设置图片列表
     *
     * @param imageList 图片列表
     */
    public void setImageList(@NonNull List<? extends Image> imageList) {
        if (this.imageList == null) {
            this.imageList = new ArrayList<>(imageList);
            notifyItemRangeInserted(0, imageList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ImageAdapter.this.imageList.size();
                }

                @Override
                public int getNewListSize() {
                    return imageList.size();
                }

                // 判断2个Item是否相同
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ImageAdapter.this.imageList.get(oldItemPosition).equals(imageList.get(newItemPosition));
                }

                // 判断2个Item的内容是否相同（只有在上面的方法判断为true后才会执行）
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(
                            ImageAdapter.this.imageList.get(oldItemPosition).getName(),
                            imageList.get(newItemPosition).getName());
                }
            });
            this.imageList = new ArrayList<>(imageList);
            result.dispatchUpdatesTo(this);
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        final ImageItemBinding binding;

        ImageViewHolder(ImageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
