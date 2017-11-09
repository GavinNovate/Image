package cn.autoio.image.view;

import android.os.Bundle;
import android.view.View;

import cn.autoio.image.R;
import cn.autoio.image.base.BaseActivity;
import cn.autoio.image.view.fragment.ImagePagerFragment;

public class ImageActivity extends BaseActivity {

    public static final String PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏隐藏导航栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.image_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, ImagePagerFragment.newInstance(getPath()))
                .commit();
    }

    private String getPath() {
        return getIntent() == null ? null : getIntent().getStringExtra(PATH);
    }
}
