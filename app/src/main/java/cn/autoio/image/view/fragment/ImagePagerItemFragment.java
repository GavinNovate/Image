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
import cn.autoio.image.databinding.ImagePagerItemFragmentBinding;
import cn.autoio.image.model.Image;
import cn.autoio.image.view.event.ImagePagerItemFragmentEvent;
import cn.autoio.image.viewmodel.ImagePagerItemViewModel;
import cn.autoio.image.viewmodel.ImagePagerViewModel;

/**
 * 图片页卡
 */
public class ImagePagerItemFragment extends Fragment {

    public static final String INDEX = "index";

    private ImagePagerItemFragmentBinding binding;
    private ImagePagerViewModel imagePagerViewModel;
    private ImagePagerItemViewModel imagePagerItemViewModel;

    public static ImagePagerItemFragment newInstance(int index) {
        ImagePagerItemFragment fragment = new ImagePagerItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_pager_item_fragment, container, false);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imagePagerViewModel = ViewModelProviders
                .of(getParentFragment())
                .get(ImagePagerViewModel.class);

        ImagePagerItemViewModel.Factory factory = new ImagePagerItemViewModel.Factory(
                getActivity().getApplication(),
                this,
                imagePagerViewModel.getImages(),
                getArguments().getInt(INDEX)
        );
        imagePagerItemViewModel = ViewModelProviders.of(this, factory).get(ImagePagerItemViewModel.class);
        init();
    }

    private void init() {
        binding.setEvent(new ImagePagerItemFragmentEvent() {
            @Override
            public void onChangeState() {
                imagePagerViewModel.setShowState(!imagePagerViewModel.getShowState().get());
            }
        });

        imagePagerItemViewModel.getImage().observe(this, new Observer<Image>() {
            @Override
            public void onChanged(@Nullable Image image) {
                if (image != null)
                    binding.setImage(image);
            }
        });
    }

    public int getIndex() {
        return getArguments().getInt(INDEX);
    }
}
