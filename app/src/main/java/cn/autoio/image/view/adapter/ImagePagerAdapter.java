package cn.autoio.image.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cn.autoio.image.view.fragment.ImagePagerItemFragment;

/**
 * author: gavin
 * created on: 2017-11-07
 * description:
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private int count;

    public ImagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ImagePagerItemFragment.newInstance(position);
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof ImagePagerItemFragment) {
            ImagePagerItemFragment fragment = (ImagePagerItemFragment) object;
            if (fragment.getIndex() < getCount()) {
                return POSITION_UNCHANGED;
            }
        }
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }
}
