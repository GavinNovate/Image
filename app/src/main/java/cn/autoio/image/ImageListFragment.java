package cn.autoio.image;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.autoio.image.databinding.ImageListFragmentBinding;
import cn.autoio.image.model.ImageEntity;
import cn.autoio.image.view.ImageAdapter;
import cn.autoio.image.viewmodel.ImageListViewModel;

public class ImageListFragment extends Fragment {

    private static final String TAG = "ImageListFragment";

    private ImageAdapter imageAdapter;

    private ImageListFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_list_fragment, container, false);
        imageAdapter = new ImageAdapter();
        binding.imageListView.setAdapter(imageAdapter);
        binding.imageListView.setLayoutManager(new GridLayoutManager(getContext(), 6));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageListViewModel viewModel = ViewModelProviders.of(getActivity()).get(ImageListViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(ImageListViewModel viewModel) {
        viewModel.getImageList().observe(this, new Observer<List<ImageEntity>>() {
            @Override
            public void onChanged(@Nullable List<ImageEntity> imageEntities) {
                if (imageEntities != null) {
                    Log.d(TAG, "onChanged: ");
                    imageAdapter.setImageList(imageEntities);
                    binding.executePendingBindings();
                }
            }
        });
    }
}
