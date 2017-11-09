package cn.autoio.image.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.autoio.image.R;
import cn.autoio.image.databinding.ImageNavigationFragmentBinding;
import cn.autoio.image.model.Image;
import cn.autoio.image.view.adapter.ImageNavigationListAdapter;
import cn.autoio.image.view.event.ImageNavigationItemEvent;
import cn.autoio.image.viewmodel.ImagePagerViewModel;

/**
 * 图片导航栏
 */
public class ImageNavigationFragment extends Fragment {

    private ImageNavigationFragmentBinding binding;
    private ImagePagerViewModel viewModel;

    private ImageNavigationListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_navigation_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getParentFragment()).get(ImagePagerViewModel.class);

        init();
    }

    private void init() {
        adapter = new ImageNavigationListAdapter(new ImageNavigationItemEvent() {
            @Override
            public void onCheck(Image image) {
                if (!image.getChecked().get())
                    viewModel.select(viewModel.getImages().getValue(), image.getPath());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        binding.imageListView.setAdapter(adapter);
        binding.imageListView.setLayoutManager(linearLayoutManager);

        viewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                if (images != null)
                    adapter.setImages(images);
            }
        });

        viewModel.getIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null)
                    binding.imageListView.scrollToPosition(integer);
            }
        });
    }
}
