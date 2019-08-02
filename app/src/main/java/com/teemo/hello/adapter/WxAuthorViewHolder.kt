package com.teemo.hello.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.teemo.hello.R
import com.teemo.hello.bean.response.WxAuthorResponse

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/31 16:39
 */
class WxAuthorViewHolder private constructor(val view: View, clickCallback: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val nameTv: AppCompatTextView = view.findViewById(R.id.author_name_tv)

    private val cardView = view.findViewById<CardView>(R.id.card_view)

    init {
        view.setOnClickListener {
            clickCallback(adapterPosition)
        }
    }

    fun bindTo(wxAuthorResponse: WxAuthorResponse?) {
        nameTv.text = wxAuthorResponse?.name ?: "正在加载..."
        cardView.setCardBackgroundColor(
            ContextCompat.getColor(
                view.context,
                if (adapterPosition % 2 == 0) R.color.colorPrimary else R.color.colorAccent
            )
        )
    }

    companion object {
        fun create(parent: ViewGroup, clickCallback: (Int) -> Unit): WxAuthorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.wx_author_item, parent, false)
            return WxAuthorViewHolder(view, clickCallback)
        }
    }
}