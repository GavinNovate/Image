package cn.autoio.image.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.autoio.image.R;
import cn.autoio.image.databinding.ImageListFragmentBinding;
import cn.autoio.image.model.Image;
import cn.autoio.image.view.ImageActivity;
import cn.autoio.image.view.adapter.ImageListAdapter;
import cn.autoio.image.view.event.ImageListFragmentEvent;
import cn.autoio.image.view.event.ImageListItemEvent;
import cn.autoio.image.viewmodel.ImageListViewModel;

public class ImageListFragment extends Fragment {

    private static final String TAG = "ImageListFragment";

    // Binding
    private ImageListFragmentBinding binding;
    // ViewModel
    private ImageListViewModel viewModel;

    // Event
    private ImageListItemEvent imageListItemEvent;
    private ImageListFragmentEvent imageListFragmentEvent;

    // Adapter
    private ImageListAdapter imageListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_list_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ImageListViewModel.class);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 图片列表条目事件
        imageListItemEvent = new ImageListItemEvent() {
            @Override
            public void onClick(Image image) {
                if (viewModel.getCheckState().get()) {
                    // 选择模式
                    viewModel.checkImage(image, !image.getChecked().get());
                } else {
                    // 启动照片浏览界面
                    startActivity(
                            new Intent(getContext(), ImageActivity.class)
                                    .putExtra(ImageActivity.PATH, image.getPath())
                    );
                }
            }

            @Override
            public boolean onLongClick(Image image) {
                // 切换 普通/选择 模式
                viewModel.setCheckState(!viewModel.getCheckState().get());
                if (viewModel.getCheckState().get()) {
                    // 当前是选择模式
                    viewModel.checkImage(image, true);
                } else {
                    // 当前是普通模式
                    viewModel.clearChecked();
                }
                return true;
            }
        };

        imageListFragmentEvent = new ImageListFragmentEvent() {
            @Override
            public void onDelete() {
                showDialog();
            }
        };

        imageListAdapter = new ImageListAdapter(viewModel.getCheckState(), imageListItemEvent);

        // binding
        binding.setCheckedCount(viewModel.getCheckedCount());
        binding.setEvent(imageListFragmentEvent);
        binding.imageListView.setAdapter(imageListAdapter);
        binding.imageListView.setLayoutManager(new GridLayoutManager(getContext(), 6));

        // viewModel
        viewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                if (images != null) {
                    imageListAdapter.setImages(images);
                    binding.executePendingBindings();
                }
            }
        });
    }

    private void showDialog() {
        new ConfirmDialogFragment().show(getChildFragmentManager(), "TAG");
    }
}
