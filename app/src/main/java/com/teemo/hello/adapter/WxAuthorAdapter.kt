package com.teemo.hello.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teemo.hello.R
import com.teemo.hello.api.RequestState
import com.teemo.hello.bean.response.WxAuthorResponse

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/31 16:04
 */
class WxAuthorAdapter(private val retryCallback: () -> Unit, private val itemClickCallback: (Int) -> Unit) :
    ListAdapter<WxAuthorResponse, RecyclerView.ViewHolder>(DiffCallback()) {
    private var networkState: RequestState = RequestState.DEFAULT
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> WxAuthorViewHolder.create(parent, itemClickCallback)
        }
    }

    private fun hasExtraRow() = networkState != RequestState.SUCCESS


    fun setNetworkState(newNetworkState: RequestState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1)
            R.layout.network_state_item
        else
            R.layout.wx_author_item

    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
            else -> (holder as WxAuthorViewHolder).bindTo(getItem(position))
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<WxAuthorResponse>() {
    override fun areItemsTheSame(oldItem: WxAuthorResponse, newItem: WxAuthorResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WxAuthorResponse, newItem: WxAuthorResponse): Boolean {
        return oldItem == newItem
    }

}