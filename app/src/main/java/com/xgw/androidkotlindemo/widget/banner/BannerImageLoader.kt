package com.xgw.androidkotlindemo.widget.banner

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xgw.androidkotlindemo.bean.response.BannerResponse
import com.youth.banner.loader.ImageLoader

/**
 *  @author: XieGuangwei
 *  @description: banner图片加载器
 *  @date: 2019/7/31 10:35
 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (context != null && imageView != null) {
            val banner = path as? BannerResponse
            Glide.with(context)
                .load(banner?.imagePath)
                .into(imageView)
        }
    }
}