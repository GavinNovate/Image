package cn.autoio.image.view;

import android.os.Bundle;

import cn.autoio.image.R;
import cn.autoio.image.base.BaseActivity;
import cn.autoio.image.view.fragment.ImageListFragment;

/**
 * 图片列表界面
 */
public class ImageListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, new ImageListFragment())
                .commit();
    }
}
