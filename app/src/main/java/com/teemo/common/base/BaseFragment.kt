package com.teemo.common.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * @author: XieGuangwei
 * @description: fragment抽象基类
 * @date: 2019/07/06
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()
    protected abstract fun initData()


    protected fun nextActivity(
        clazz: Class<*>,
        bundle: Bundle? = null,
        forResultRequestCode: Int? = null
    ) {

        val intent = Intent()
        context?.apply {
            intent.setClass(this, clazz)
        }
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        if (forResultRequestCode != null) {
            startActivityForResult(intent, forResultRequestCode)
        } else {
            startActivity(intent)
        }
    }

    protected fun showSnackBar(view: View?, msg: String) {
        view?.let {
            Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}