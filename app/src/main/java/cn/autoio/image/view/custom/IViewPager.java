package cn.autoio.image.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author: gavin
 * created on: 2017-11-07
 * description:
 */
public class IViewPager extends ViewPager {
    public IViewPager(Context context) {
        super(context);
    }

    public IViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 可能是系统BUG，捕获掉防止崩溃
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // 可能是系统BUG，捕获掉防止崩溃
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
