package cn.autoio.image.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.autoio.image.R;
import cn.autoio.image.databinding.ImageNavigationItemBinding;
import cn.autoio.image.model.Image;
import cn.autoio.image.view.event.ImageNavigationItemEvent;

/**
 * author: gavin
 * created on: 2017-11-09
 * description:
 */
public class ImageNavigationListAdapter extends RecyclerView.Adapter<ImageNavigationListAdapter.ImageViewHolder> {

    // 数据
    private List<Image> images;
    // 事件
    private ImageNavigationItemEvent event;

    public ImageNavigationListAdapter(ImageNavigationItemEvent event) {
        this.event = event;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageNavigationItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.image_navigation_item,
                parent,
                false
        );
        binding.setEvent(event);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.binding.setImage(images.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    /**
     * 设置图片列表
     *
     * @param images 图片列表
     */
    public void setImages(@NonNull List<Image> images) {
        if (this.images == null) {
            this.images = new ArrayList<>(images);
            notifyItemRangeInserted(0, images.size());
        } else {
            // 取出旧数据的ObservableFiled绑定到新数据
            for (Image newImage : images) {
                for (Image oldImage : this.images) {
                    if (newImage.equals(oldImage)) {
                        newImage.setChecked(oldImage.getChecked());
                    }
                }
            }
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ImageNavigationListAdapter.this.images.size();
                }

                @Override
                public int getNewListSize() {
                    return images.size();
                }

                // 判断2个Item是否相同
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ImageNavigationListAdapter.this.images.get(oldItemPosition).equals(images.get(newItemPosition));
                }

                // 判断2个Item的内容是否相同（只有在上面的方法判断为true后才会执行）
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return ImageNavigationListAdapter.this.images.get(oldItemPosition).getChecked().get() == images.get(newItemPosition).getChecked().get();
                }
            });
            this.images = new ArrayList<>(images);
            result.dispatchUpdatesTo(this);
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        final ImageNavigationItemBinding binding;

        ImageViewHolder(ImageNavigationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
