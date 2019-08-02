package com.teemo.hello.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.teemo.hello.R
import com.teemo.hello.base.BaseActivity
import com.teemo.hello.widget.transformer.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_profile.*

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/16 11:22
 */
class ProfileActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_profile

    override fun initView() {
        setSupportActionBar(toolbar)
        //toolbar
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        //tab names
        val tabNames = arrayOf("登录", "注册")
        //fragment adapter
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return tabNames.size
            }

            override fun createFragment(position: Int): Fragment {
                return LoginOrRegisterFragment.create(position)
            }
        }

        //transformer
        viewPager2.setPageTransformer(DepthPageTransformer())
        //bind viewPager and tabLayout
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    override fun initData() {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val PROFILE_TYPE_LOGIN = 0
        const val PROFILE_TYPE_REGISTER = 1
    }
}