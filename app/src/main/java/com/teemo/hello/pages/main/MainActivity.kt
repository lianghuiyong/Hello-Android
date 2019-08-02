package com.teemo.hello.pages.main

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.Group
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.teemo.hello.R
import com.teemo.common.base.BaseActivity
import com.teemo.hello.pages.profile.ProfileActivity
import com.teemo.hello.utils.InjectorUtils
import com.teemo.hello.viewmodels.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * @author: XieGuangwei
 * @description: 主页
 * @date: 2019/07/06
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val mainViewModel by viewModels<MainViewModel> {
        InjectorUtils.provideMainViewModelFactory(this)
    }
    var count = 0

    override fun getLayoutId() = R.layout.activity_main

    override fun initView() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            nextActivity(ProfileActivity::class.java)
//            showSnackBar(it, "点我干嘛")
        }
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val headerView = nav_view.inflateHeaderView(R.layout.nav_header_main)
        val iconIv = headerView.findViewById<AppCompatImageView>(R.id.icon_header_iv)//头像暂时不加
        val headerName = headerView.findViewById<AppCompatTextView>(R.id.name_tv)
        val headerGroup = headerView.findViewById<Group>(R.id.icon_name_group)
        val loginButton = headerView.findViewById<AppCompatButton>(R.id.login_btn)
        loginButton.setOnClickListener {
            nextActivity(ProfileActivity::class.java)
        }
        mainViewModel.hasLoginLiveData.observe(this, Observer {
            with(headerGroup) {
                visibility = if (it) View.VISIBLE else View.INVISIBLE
            }
            with(loginButton) {
                visibility = if (it) View.INVISIBLE else View.VISIBLE
            }
        })

        mainViewModel.getUserLiveData().observe(this, Observer {
            headerName.text = it?.name
        })
    }

    override fun initData() {

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                //此处模拟数据库更新
                count++
                mainViewModel.updateUserName("名字$count")
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
//        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
