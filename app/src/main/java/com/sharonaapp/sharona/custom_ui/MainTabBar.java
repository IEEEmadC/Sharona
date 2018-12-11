package com.sharonaapp.sharona.custom_ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;


public class MainTabBar extends LinearLayout {
    private ViewPager viewPager;
    private final PageListener pageListener = new PageListener();

    public ViewPager.OnPageChangeListener delegatePageListener;

    public MainTabBar(Context context) {
        super(context);
    }

    public MainTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MainTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewPager(ViewPager viewPager1) {
        viewPager = viewPager1;
        viewPager.addOnPageChangeListener(pageListener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View tabs = inflate(getContext(), R.layout.include_bottom_navigation, this);
        initTabs(tabs);

    }

    private void initTabs(View tabs) {
//        final ViewGroup tabRoot = (ViewGroup) tabs.findViewById(R.id.bottom_navigation);
//        for (int i = 0; i < tabRoot.getChildCount(); i++) {
//            final int finalI = i;
//            tabRoot.getChildAt(i).setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (finalI == viewPager.getCurrentItem()) {
//                        ((MainActivity) getContext()).getMainPagerAdapter().getItem(finalI).back();
//                    }
//                    viewPager.setCurrentItem(finalI, false);
//                }
//            });
//        }
    }


    private class PageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }

            final ViewGroup tabRoot = (ViewGroup) findViewById(R.id.bottom_navigation);
            for (int i = 0; i < tabRoot.getChildCount(); i++) {
                ViewGroup child = (ViewGroup) tabRoot.getChildAt(i);
                for (int j = 0; j < child.getChildCount(); j++) {
                    child.getChildAt(j).setSelected(false);
                }
                tabRoot.getChildAt(i).setSelected(false);
            }
            ViewGroup selectedChild = (ViewGroup) tabRoot.getChildAt(position);
            for (int j = 0; j < selectedChild.getChildCount(); j++) {
                selectedChild.getChildAt(j).setSelected(true);
            }
            tabRoot.getChildAt(position).setSelected(true);
        }

    }

}

