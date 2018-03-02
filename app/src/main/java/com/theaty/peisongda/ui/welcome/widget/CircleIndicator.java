package com.theaty.peisongda.ui.welcome.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.theaty.peisongda.R;

import foundation.util.ScreenUtils;

/**
 * Created by Administrator on 2016/9/14.
 */
public class CircleIndicator extends LinearLayout {
    private static final float CIRCLE_SIZE = 10;
    private static final int MARGIN = 10;
    private int mIndicatorCount;

    public CircleIndicator(Context context) {
        super(context);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setViewPager(ViewPager viewPager) {
        mIndicatorCount = viewPager.getAdapter().getCount();
        initIndicator(mIndicatorCount);
        viewPager.addOnPageChangeListener(new IndicatorPagerChangerListener());
    }

    private void initIndicator(int count) {
        for(int i = 0; i < count; i++) {
            View indicator = new View(getContext());
            LayoutParams params = new LayoutParams(ScreenUtils.dip2px(CIRCLE_SIZE), ScreenUtils.dip2px(CIRCLE_SIZE));
            params.setMargins(ScreenUtils.dip2px(MARGIN),ScreenUtils.dip2px(MARGIN),ScreenUtils.dip2px(MARGIN),ScreenUtils.dip2px(MARGIN));
            indicator.setLayoutParams(params);
            indicator.setBackgroundResource(R.drawable.point);
            addView(indicator);
        }
        activeIndicator(0);
    }

    private void activeIndicator(int order) {
        for(int i = 0; i < mIndicatorCount; i++) {
            getChildAt(i).setEnabled(false);
        }
            getChildAt(order).setEnabled(true);
    }

    private class IndicatorPagerChangerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
activeIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
