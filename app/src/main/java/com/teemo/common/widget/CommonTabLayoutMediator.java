/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teemo.common.widget;

import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.teemo.hello.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static androidx.viewpager2.widget.ViewPager2.*;

/**
 * A mediator to link a TabLayout with a ViewPager2. The mediator will synchronize the ViewPager2's
 * position with the selected tab when a tab is selected, and the TabLayout's scroll position when
 * the user drags the ViewPager2.
 *
 * <p>Establish the link by creating an instance of this class, make sure the ViewPager2 has an
 * adapter and then call {@link #attach()} on it. When creating an instance of this class, you must
 * supply an implementation of {@link OnConfigureTabCallback} in which you set the text of the tab,
 * and/or perform any styling of the tabs that you require.
 */
public final class CommonTabLayoutMediator {
  @NonNull private final TabLayout tabLayout;
  @NonNull private final ViewPager2 viewPager;
  @NonNull private final ArrayList<String> tabData;
  private final boolean autoRefresh;
  private RecyclerView.Adapter<?> adapter;
  private boolean attached;

  private TabLayoutOnPageChangeCallback onPageChangeCallback;
  private TabLayout.OnTabSelectedListener onTabSelectedListener;
  private RecyclerView.AdapterDataObserver pagerAdapterObserver;

  /**
   * A callback interface that must be implemented to set the text and styling of newly created
   * tabs.
   */
  public interface OnConfigureTabCallback {
    /**
     * Called to configure the tab for the page at the specified position. Typically calls {@link
     * TabLayout.Tab#setText(CharSequence)}, but any form of styling can be applied.
     *
     * @param tab The Tab which should be configured to represent the title of the item at the given
     *     position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    void onConfigureTab(@NonNull TabLayout.Tab tab, int position);
  }

  public CommonTabLayoutMediator(
      @NonNull TabLayout tabLayout,
      @NonNull ViewPager2 viewPager,
      @NonNull ArrayList<String> tabData) {
    this(tabLayout, viewPager,tabData, true);
  }

  public CommonTabLayoutMediator(
      @NonNull TabLayout tabLayout,
      @NonNull ViewPager2 viewPager,
      @NonNull ArrayList<String> tabData,
      boolean autoRefresh) {
    this.tabLayout = tabLayout;
    this.viewPager = viewPager;
    this.tabData = tabData;
    this.autoRefresh = autoRefresh;
  }

  /**
   * Link the TabLayout and the ViewPager2 together.
   *
   * @throws IllegalStateException If the mediator is already attached, or the ViewPager2 has no
   *     adapter.
   */
  public void attach() {
    if (attached) {
      throw new IllegalStateException("TabLayoutMediator is already attached");
    }
    adapter = viewPager.getAdapter();
    if (adapter == null) {
      throw new IllegalStateException(
          "TabLayoutMediator attached before ViewPager2 has an " + "adapter");
    }
    attached = true;

    // Add our custom OnPageChangeCallback to the ViewPager
    onPageChangeCallback = new TabLayoutOnPageChangeCallback(tabLayout);
    viewPager.registerOnPageChangeCallback(onPageChangeCallback);

    // Now we'll add a tab selected listener to set ViewPager's current item
    onTabSelectedListener = new ViewPagerOnTabSelectedListener(viewPager);
    tabLayout.addOnTabSelectedListener(onTabSelectedListener);

    // Now we'll populate ourselves from the pager adapter, adding an observer if
    // autoRefresh is enabled
    if (autoRefresh) {
      // Register our observer on the new adapter
      pagerAdapterObserver = new PagerAdapterObserver();
      adapter.registerAdapterDataObserver(pagerAdapterObserver);
    }

    populateTabsFromPagerAdapter();

    // Now update the scroll position to match the ViewPager's current item
    tabLayout.setScrollPosition(viewPager.getCurrentItem(), 0f, true);
  }

  /** Unlink the TabLayout and the ViewPager */
  public void detach() {
    adapter.unregisterAdapterDataObserver(pagerAdapterObserver);
    tabLayout.removeOnTabSelectedListener(onTabSelectedListener);
    viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
    pagerAdapterObserver = null;
    onTabSelectedListener = null;
    onPageChangeCallback = null;
    attached = false;
  }

  @SuppressWarnings("WeakerAccess")
  void populateTabsFromPagerAdapter() {
    tabLayout.removeAllTabs();

    if (adapter != null) {
      int adapterCount = adapter.getItemCount();
      for (int i = 0; i < adapterCount; i++) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setCustomView(getTabView(tabData.get(i)));
        tabLayout.addTab(tab, false);
      }

      // Make sure we reflect the currently set ViewPager item
      if (adapterCount > 0) {
        int currItem = viewPager.getCurrentItem();
        if (currItem != tabLayout.getSelectedTabPosition()) {
          tabLayout.getTabAt(currItem).select();
        }
      }
    }
  }

  /**
   * 自定义方法，提供自定义Tab
   *
   * @param tabName
   * @return 返回Tab的View
   *
   * UI_MENU_ICON_TEXT（App底部的三个样式）
   * UI_MENU_BIG_TEXT （首页上面大标题样式）
   * UI_MENU_RADIUS_BTN （圆角样式）
   *
   */
  private View getTabView(String tabName) {
    View view = LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.common_tab_layout_menu_text, null);

    //文字内容
    TextView textView = view.findViewById(R.id.tv_title);
    textView.setText(tabName);
    return view;
  }

  /**
   * A {@link ViewPager2.OnPageChangeCallback} class which contains the necessary calls back to the
   * provided {@link TabLayout} so that the tab position is kept in sync.
   *
   * <p>This class stores the provided TabLayout weakly, meaning that you can use {@link
   * ViewPager2#registerOnPageChangeCallback(ViewPager2.OnPageChangeCallback)} without removing the
   * callback and not cause a leak.
   */
  private static class TabLayoutOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
    private final WeakReference<TabLayout> tabLayoutRef;
    private int previousScrollState;
    private int scrollState;

    TabLayoutOnPageChangeCallback(TabLayout tabLayout) {
      tabLayoutRef = new WeakReference<>(tabLayout);
      reset();
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
      previousScrollState = scrollState;
      scrollState = state;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      TabLayout tabLayout = tabLayoutRef.get();
      if (tabLayout != null) {
        // Only update the text selection if we're not settling, or we are settling after
        // being dragged
        boolean updateText =
            scrollState != SCROLL_STATE_SETTLING || previousScrollState == SCROLL_STATE_DRAGGING;
        // Update the indicator if we're not settling after being idle. This is caused
        // from a setCurrentItem() call and will be handled by an animation from
        // onPageSelected() instead.
        boolean updateIndicator =
            !(scrollState == SCROLL_STATE_SETTLING && previousScrollState == SCROLL_STATE_IDLE);
        tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);

        upTabSelected(position, positionOffset);
      }
    }

    //选中样式初始化
    private void upTabSelected(int position, float positionOffset) {
      float textScale1 = 1 - positionOffset;
      float scaleBase = 0.85f;
      float scale1 = scaleBase + (1 - scaleBase) * textScale1;
      float scale2 = scaleBase + (1 - scaleBase) * positionOffset;

      TextView textView1 = tabLayoutRef.get().getTabAt(position).getCustomView().findViewById(R.id.tv_title);
      textView1.setScaleX(scale1);
      textView1.setScaleY(scale1);

      if (position < tabLayoutRef.get().getTabCount() - 1 ){
        TextView textView2 = tabLayoutRef.get().getTabAt(position+1).getCustomView().findViewById(R.id.tv_title);
        textView2.setScaleX(scale2);
        textView2.setScaleY(scale2);
      }
    }

    @Override
    public void onPageSelected(final int position) {
      TabLayout tabLayout = tabLayoutRef.get();
      if (tabLayout != null
          && tabLayout.getSelectedTabPosition() != position
          && position < tabLayout.getTabCount()) {
        // Select the tab, only updating the indicator if we're not being dragged/settled
        // (since onPageScrolled will handle that).
        boolean updateIndicator =
            scrollState == SCROLL_STATE_IDLE
                || (scrollState == SCROLL_STATE_SETTLING
                    && previousScrollState == SCROLL_STATE_IDLE);
        tabLayout.selectTab(tabLayout.getTabAt(position), updateIndicator);
      }
    }

    void reset() {
      previousScrollState = scrollState = SCROLL_STATE_IDLE;
    }
  }

  /**
   * A {@link TabLayout.OnTabSelectedListener} class which contains the necessary calls back to the
   * provided {@link ViewPager2} so that the tab position is kept in sync.
   */
  private static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
    private final ViewPager2 viewPager;

    ViewPagerOnTabSelectedListener(ViewPager2 viewPager) {
      this.viewPager = viewPager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
      viewPager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
      // No-op
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
      // No-op
    }
  }

  private class PagerAdapterObserver extends RecyclerView.AdapterDataObserver {
    PagerAdapterObserver() {}

    @Override
    public void onChanged() {
      populateTabsFromPagerAdapter();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
      populateTabsFromPagerAdapter();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
      populateTabsFromPagerAdapter();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      populateTabsFromPagerAdapter();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      populateTabsFromPagerAdapter();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
      populateTabsFromPagerAdapter();
    }
  }
}
