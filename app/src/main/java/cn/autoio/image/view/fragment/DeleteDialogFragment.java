package cn.autoio.image.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import cn.autoio.image.R;
import cn.autoio.image.databinding.DeleteDialogFragmentBinding;
import cn.autoio.image.view.event.ConfirmDialogFragmentEvent;
import cn.autoio.image.viewmodel.ImagePagerViewModel;

/**
 * author: gavin
 * created on: 2017-11-03
 * description:确认对话框
 * 照片浏览界面的删除确认对话框
 * 可以与ConfirmDialogFragment合并
 */
public class DeleteDialogFragment extends DialogFragment {

    private DeleteDialogFragmentBinding binding;
    private ImagePagerViewModel viewModel;

    private ConfirmDialogFragmentEvent event;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(inflater, R.layout.delete_dialog_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getParentFragment().getParentFragment()).get(ImagePagerViewModel.class);

        init();
    }

    private void init() {
        event = new ConfirmDialogFragmentEvent() {
            @Override
            public void onDelete() {
                viewModel.deleteImage(viewModel.getImage().getValue());
                dismiss();
            }

            @Override
            public void onCancel() {
                dismiss();
            }
        };
        binding.setEvent(event);
    }
}
