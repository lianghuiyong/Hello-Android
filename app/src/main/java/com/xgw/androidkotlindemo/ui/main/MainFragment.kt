package com.xgw.androidkotlindemo.ui.main

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.xgw.androidkotlindemo.R
import com.xgw.androidkotlindemo.adapter.WxAuthorAdapter
import com.xgw.androidkotlindemo.api.RequestState
import com.xgw.androidkotlindemo.base.BaseFragment
import com.xgw.androidkotlindemo.bean.response.BannerResponse
import com.xgw.androidkotlindemo.ui.web.WebViewActivity
import com.xgw.androidkotlindemo.utils.InjectorUtils
import com.xgw.androidkotlindemo.viewmodels.main.MainViewModel
import com.xgw.androidkotlindemo.widget.banner.BannerImageLoader
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *  @author: XieGuangwei
 *  @description: 主页主体内容
 *  @date: 2019/7/31 10:21
 */
class MainFragment : BaseFragment() {
    private val banners = mutableListOf<BannerResponse>()
    private val viewModel by viewModels<MainViewModel> {
        InjectorUtils.provideMainViewModelFactory(requireContext())
    }
    private lateinit var adapter: WxAuthorAdapter

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
            val args = Bundle()
            args.putString("title", banners[it].title)
            args.putString("url", banners[it].url)
            nextActivity(WebViewActivity::class.java, args)
        }

        adapter = WxAuthorAdapter({
            adapter.setNetworkState(RequestState.LOADING)
            viewModel.getBanner()
        }) {
            adapter.setNetworkState(RequestState.SUCCESS)
        }
        recyclerView.adapter = adapter
        viewModel.wxResponses.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.requestState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })

        viewModel.getWxAuthors()
    }
}