package com.teemo.hello.ui.main

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.teemo.hello.R
import com.teemo.hello.adapter.WxAuthorAdapter
import com.teemo.hello.api.RequestState
import com.teemo.hello.base.BaseFragment
import com.teemo.hello.bean.response.BannerResponse
import com.teemo.hello.ui.web.WebViewActivity
import com.teemo.hello.utils.InjectorUtils
import com.teemo.hello.viewmodels.main.MainViewModel
import com.teemo.hello.widget.banner.BannerImageLoader
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