package com.theaty.peisongda.ui.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.theaty.peisongda.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import foundation.base.fragment.BaseFragment;
import foundation.notification.NotificationCenter;
import foundation.notification.NotificationListener;

import static com.theaty.peisongda.notification.NotificationKey.ChangeTab;


/**
 * Created by li on 2017/2/26.
 */

public class OrderFragmentNew extends BaseFragment implements NotificationListener {


    @BindView(R.id.tab_select_manager)
    TabLayout tabSelectManager;
    @BindView(R.id.view_pager_order)
    ViewPager viewPagerOrder;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.order_rg)
    RadioGroup orderRg;
    Unbinder unbinder;
    private ArrayList<BaseFragment> baseFragments;
    private String[] titles = {"未处理订单", "已处理订单"};
    private int[] type = {1, 2};
    private int[] state = {0, 0};
    private MyAadapter myAadapter;

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.home_fragment1);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        orderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton rb = (RadioButton) getView().findViewById(radioGroup.getCheckedRadioButtonId());
                if (rb.getText().toString().equals("未处理订单")) {
                    viewPagerOrder.setCurrentItem(0);

                } else if (rb.getText().toString().equals("已处理订单")) {
                    viewPagerOrder.setCurrentItem(1);

                }
            }
        });
        viewPagerOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                currentItem = position;
                ((RadioButton) orderRg.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        NotificationCenter.defaultCenter.addListener(ChangeTab, this);
    }


    @Override
    protected void goNext() {
        super.goNext();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        NotificationCenter.defaultCenter.removeListener(ChangeTab, this);
        unbinder.unbind();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public boolean onNotification(Notification notification) {
        if (notification.key.equals(ChangeTab)) {
            viewPagerOrder.setCurrentItem(1);
            tabSelectManager.getTabAt(1).select();

            return true;
        }
        return false;
    }

    public static String formatBadgeNumber(int value) {
        if (value <= 0) {
            return null;
        }

        if (value < 1000) {
//         equivalent to String#valueOf(int);
            return Integer.toString(value);
        }

        // my own policy
        return "999+";
    }

//    public View getTabItemView(int position) {
//
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout_item, null);
//        TextView textView = (TextView) view.findViewById(R.id.textview);
//        textView.setText(titles[position]);
//
//        View target = view.findViewById(R.id.badgeview_target);
//
//        BadgeView badgeView = new BadgeView(getActivity());
//        badgeView.setTargetView(target);
//        badgeView.setTextSize(10);
//        if (state[position] == 0) {
//            target.setVisibility(View.GONE);
//        } else {
//            target.setVisibility(View.VISIBLE);
//            badgeView.setText(formatBadgeNumber(state[position]));
//        }
//        return view;
//
//    }

    private void initView() {
        baseFragments = new ArrayList<>();
        baseFragments.add(new OrderNewFragment());
        baseFragments.add(new OrderDoneFragment());
        myAadapter = new MyAadapter(getChildFragmentManager());
        viewPagerOrder.setAdapter(myAadapter);
        tabSelectManager.setupWithViewPager(viewPagerOrder);
//        setUpTabBadge();
    }


//        state[0]++;
//        setUpTabBadge();


    public class MyAadapter extends FragmentPagerAdapter {
        public MyAadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return baseFragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }


//    private void setUpTabBadge() {
//        for (int i = 0; i < baseFragments.size(); i++) {
//            TabLayout.Tab tab = tabSelectManager.getTabAt(i);
//
//            // 更新Badge前,先remove原来的customView,否则Badge无法更新
//            View customView = tab.getCustomView();
//            if (customView != null) {
//                ViewParent parent = customView.getParent();
//                if (parent != null) {
//                    ((ViewGroup) parent).removeView(customView);
//                }
//            }
//
//            // 更新CustomView
//            tab.setCustomView(getTabItemView(i));
//        }
//
//        // 需加上以下代码,不然会出现更新Tab角标后,选中的Tab字体颜色不是选中状态的颜色
//        tabSelectManager.getTabAt(tabSelectManager.getSelectedTabPosition()).getCustomView().setSelected(true);
//    }

}