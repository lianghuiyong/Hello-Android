package com.xgw.androidkotlindemo.ui.main

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.xgw.androidkotlindemo.R
import com.xgw.androidkotlindemo.base.BaseFragment
import com.xgw.androidkotlindemo.bean.response.BannerResponse
import com.xgw.androidkotlindemo.ui.web.WebViewActivity
import com.xgw.androidkotlindemo.utils.InjectorUtils
import com.xgw.androidkotlindemo.viewmodels.main.MainViewModel
import com.xgw.androidkotlindemo.widget.banner.BannerImageLoader
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/31 10:21
 */
class MainFragment : BaseFragment() {
    private val banners = mutableListOf<BannerResponse>()
    private val viewModel by viewModels<MainViewModel> {
        InjectorUtils.provideMainViewModelFactory(requireContext())
    }

    override fun getLayoutId() = R.layout.fragment_main

    override fun initView() {
        banner.setImageLoader(BannerImageLoader())
    }

    override fun initData() {
        viewModel.banner.observe(viewLifecycleOwner, Observer {
            banner.setImages(it)
            banner.start()
            banners.addAll(it)
        })
        viewModel.getBanner()
        banner.setOnBannerListener {
            val title = banners[it].title
            val url = banners[it].url
            val args = Bundle()
            args.putString("title", title)
            args.putString("url", url)
            nextActivity(WebViewActivity::class.java, args)
        }
    }
}