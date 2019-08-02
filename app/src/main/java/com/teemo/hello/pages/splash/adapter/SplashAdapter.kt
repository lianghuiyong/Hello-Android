package com.teemo.hello.pages.splash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teemo.common.adapter.BaseViewHolder
import com.teemo.hello.R

class SplashAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val image = holder.getView<AppCompatImageView>(R.id.image)
        Glide.with(image.context).load(data?.get(position)).into(image)
    }

    fun setNewData(list: ArrayList<Int>){
        this.data = list
        notifyDataSetChanged()
    }

    private var data : ArrayList<Int> ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_splash_view, parent, false))
    }

    override fun getItemCount(): Int {
        return data!!.size
    }
}