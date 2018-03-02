package com.theaty.peisongda.ui.welcome;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.theaty.peisongda.R;
import com.theaty.peisongda.system.DatasStore;
import com.theaty.peisongda.ui.MainActivity;
import com.theaty.peisongda.ui.welcome.widget.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import foundation.base.activity.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;

    private int[] imageIds = {R.drawable.welcome_1, R.drawable.welcome_2, R.drawable.welcome_3};
    private List<ImageView> imageViews = new ArrayList<>();

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
    }

    private void initView() {
        for (int id : imageIds) {
            ImageView imageView = new ImageView(this);
            // TODO: 2016/10/25 第三方加载图片
            Glide.with(this).load(id).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }

        WelcomePagerAdapter mAdaper = new WelcomePagerAdapter(imageViews);
        mPager.setAdapter(mAdaper);
        mIndicator.setViewPager(mPager);
    }

    private class WelcomePagerAdapter extends PagerAdapter {
        List<ImageView> imageViews;

        public WelcomePagerAdapter(List<ImageView> imageViews) {
            this.imageViews = imageViews;
        }

        @Override
        public int getCount() {
            if (imageViews != null) return imageViews.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            if (position == getCount() - 1) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatasStore.setFirstLaunch(false);


                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));

                        finish();

                    }
                });
            }
            container.addView(imageView);
            return imageView;
        }
    }


}
