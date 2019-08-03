package com.teemo.hello.pages.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.teemo.common.base.BaseActivity
import com.teemo.hello.R
import com.teemo.hello.pages.main.fragments.MainFragment
import com.teemo.hello.pages.main.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main2.*

class MainPage : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_main2

    override fun initData() {
        QMUIStatusBarHelper.setStatusBarLightMode(this)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 -> HomeFragment()
                    else -> MainFragment()
                }
            }

            override fun getItemCount(): Int {
                return 2
            }
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0 ->  tab.text = "推荐"
                else ->  tab.text = "电台"
            }
        }.attach()
    }
}