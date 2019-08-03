package com.teemo.common.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.annotation.AttrRes

class CommonWebProgress : ProgressBar {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    fun upProgress(newProgress: Int){
        when {
            newProgress < 30 -> upAnim(30, 350, 0)
            newProgress < 100 -> upAnim( newProgress, 350, 0)
            else -> upAnim(100, 200, 400)
        }
    }

    private var mAnimator: ObjectAnimator? = null
    private fun upAnim(newProgress: Int, duration: Long, delayMillis : Long){
        if (mAnimator != null && mAnimator!!.isRunning) {
            mAnimator?.pause()
        }
        mAnimator = ObjectAnimator.ofInt(this, "progress", newProgress )
        mAnimator?.duration = duration
        mAnimator?.interpolator = DecelerateInterpolator()
        mAnimator?.start()

        postDelayed({
            when{
                newProgress >= 100 ->{
                    progress = 0
                    visibility = View.GONE
                }

                else -> {
                    visibility = View.VISIBLE
                }
            }
        }, delayMillis)
    }
}