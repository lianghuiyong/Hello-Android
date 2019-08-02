package com.teemo.common.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.AttrRes

class CommonWebProgress : ProgressBar {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    fun upProgress(newProgress: Int){
        when {
            newProgress < 30 -> upAnim(30, 500, 0)
            newProgress < 100 -> upAnim( newProgress, 100, 0)
            else -> upAnim(100, 200, 400)
        }
    }

    private var mAnimator: ObjectAnimator? = null
    private fun upAnim(newProgress: Int, duration: Long, delayMillis : Long){
        if (mAnimator != null && mAnimator!!.isRunning) {
            mAnimator!!.cancel()
        }
        mAnimator = ObjectAnimator.ofInt(this, "progress", newProgress )
        mAnimator?.duration = duration
        mAnimator?.start()

        postDelayed({
            visibility = if (newProgress >= 100) View.GONE else View.VISIBLE
        }, delayMillis)
    }
}