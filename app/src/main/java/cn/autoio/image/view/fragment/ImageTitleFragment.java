package cn.autoio.image.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.autoio.image.R;
import cn.autoio.image.databinding.ImageTitleFragmentBinding;
import cn.autoio.image.model.Image;
import cn.autoio.image.view.event.ImageTitleFragmentEvent;
import cn.autoio.image.viewmodel.ImagePagerViewModel;

/**
 * 图片标题栏
 */
public class ImageTitleFragment extends Fragment {

    private ImageTitleFragmentBinding binding;
    private ImagePagerViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_title_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getParentFragment()).get(ImagePagerViewModel.class);

        init();
    }

    private void init() {
        binding.setEvent(new ImageTitleFragmentEvent() {
            @Override
            public void onBack() {
                getActivity().finish();
            }

            @Override
            public void onDelete(Image image) {
                new DeleteDialogFragment().show(getChildFragmentManager(), "TAG");
            }
        });

        viewModel.getImage().observe(this, new Observer<Image>() {
            @Override
            public void onChanged(@Nullable Image image) {
                binding.setImage(image);
            }
        });
    }
}
