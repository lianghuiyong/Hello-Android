package com.teemo.common.swipe

import com.qmuiteam.qmui.util.QMUIDisplayHelper

open class BaseSwipeBackActivity : QMUIActivity() {

    override fun backViewInitOffset(): Int {
        return QMUIDisplayHelper.dp2px(this, 100)
    }

    override fun canDragBack(): Boolean {
        return true
    }
}