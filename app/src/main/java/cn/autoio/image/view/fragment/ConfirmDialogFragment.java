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
import cn.autoio.image.databinding.ConfirmDialogFragmentBinding;
import cn.autoio.image.view.event.ConfirmDialogFragmentEvent;
import cn.autoio.image.viewmodel.ImageListViewModel;

/**
 * author: gavin
 * created on: 2017-11-03
 * description:确认对话框
 * 照片列表的删除确认对话框
 */
public class ConfirmDialogFragment extends DialogFragment {

    private ConfirmDialogFragmentBinding binding;
    private ImageListViewModel viewModel;

    private ConfirmDialogFragmentEvent event;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(inflater, R.layout.confirm_dialog_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ImageListViewModel.class);

        init();
    }

    private void init() {
        event = new ConfirmDialogFragmentEvent() {
            @Override
            public void onDelete() {
                viewModel.deleteImages();
                viewModel.getCheckState().set(false);
                dismiss();
            }

            @Override
            public void onCancel() {
                viewModel.getCheckState().set(false);
                viewModel.clearChecked();
                dismiss();
            }
        };

        binding.setCount(viewModel.getCheckedCount());
        binding.setEvent(event);
    }
}
