package cn.autoio.image.view.fragment;


import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.autoio.image.R;
import cn.autoio.image.databinding.ImagePagerFragmentBinding;
import cn.autoio.image.model.Image;
import cn.autoio.image.view.adapter.ImagePagerAdapter;
import cn.autoio.image.view.event.ImagePagerFragmentEvent;
import cn.autoio.image.viewmodel.ImagePagerViewModel;

public class ImagePagerFragment extends Fragment {

    private static final String TAG = "ImagePagerFragment";

    public static final String PATH = "path";

    private ImagePagerFragmentBinding binding;
    private ImagePagerViewModel viewModel;

    private ImagePagerAdapter adapter;

    public static ImagePagerFragment newInstance(String path) {

        Bundle bundle = new Bundle();
        bundle.putString(PATH, path);
        ImagePagerFragment fragment = new ImagePagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_pager_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.titleFrame, new ImageTitleFragment())
                .replace(R.id.navigationFrame, new ImageNavigationFragment())
                .commit();
        initAnimation();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ImagePagerViewModel.class);

        init();
    }

    private void init() {
        adapter = new ImagePagerAdapter(getChildFragmentManager());

        binding.setShowState(viewModel.getShowState());
        binding.setEvent(new ImagePagerFragmentEvent() {
            @Override
            public void onContraRotate() {
                viewModel.contraRotate();
            }
        });
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewModel.selected(position);
                if (viewModel.getImage().getValue() != null)
                    getArguments().putString(PATH, viewModel.getImage().getValue().getPath());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                if (images != null) {
                    adapter.setCount(images.size());
                    viewModel.select(images, getArguments().getString(PATH));
                }
            }
        });

        viewModel.getPager().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer index) {
                if (index != null && index >= 0) {
                    if (index == binding.viewPager.getCurrentItem()) {
                        viewModel.selected(index);
                    } else {
                        binding.viewPager.setCurrentItem(index);
                    }
                }
            }
        });
    }

    /**
     * 初始化标题栏和导航栏显示隐藏动画
     */
    private void initAnimation() {
        // 100dp的高度
        float height = getResources().getDisplayMetrics().density * 100;
        // 设置标题栏布局的动画效果
        LayoutTransition titleLayoutTransition = new LayoutTransition();
        ObjectAnimator titleAnimatorDown = ObjectAnimator.ofFloat(null, "translationY", -height, 0f);
        ObjectAnimator titleAnimatorUp = ObjectAnimator.ofFloat(null, "translationY", 0f, -height);
        titleLayoutTransition.setAnimator(LayoutTransition.APPEARING, titleAnimatorDown);
        titleLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, titleAnimatorUp);
        binding.titleLayout.setLayoutTransition(titleLayoutTransition);
        // 设置导航栏布局的动画效果
        LayoutTransition navigationLayoutTransition = new LayoutTransition();
        ObjectAnimator navigationAnimatorUp = ObjectAnimator.ofFloat(null, "translationY", height, 0f);
        ObjectAnimator navigationAnimatorDown = ObjectAnimator.ofFloat(null, "translationY", 0f, height);
        navigationLayoutTransition.setAnimator(LayoutTransition.APPEARING, navigationAnimatorUp);
        navigationLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, navigationAnimatorDown);
        binding.navigationLayout.setLayoutTransition(navigationLayoutTransition);
    }
}
