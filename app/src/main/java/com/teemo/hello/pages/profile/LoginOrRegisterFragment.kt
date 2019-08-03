package com.teemo.hello.pages.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.teemo.hello.R
import com.teemo.hello.api.RequestState
import com.teemo.common.base.BaseFragment
import com.teemo.hello.bean.send.ProfileSendBean
import com.teemo.hello.pages.profile.ProfilePage.Companion.PROFILE_TYPE_LOGIN
import com.teemo.hello.pages.profile.ProfilePage.Companion.PROFILE_TYPE_REGISTER
import com.teemo.hello.utils.InjectorUtils
import com.teemo.hello.viewmodels.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_login_or_register.*

/**
 *  @author: XieGuangwei
 *  @description: 登录/注册
 *  @date: 2019/7/16 14:35
 */
class LoginOrRegisterFragment : BaseFragment() {
    private val profileViewModel by viewModels<ProfileViewModel> {
        InjectorUtils.provideProfileViewModelFactory(requireContext())
    }

    override fun getLayoutId(): Int = R.layout.fragment_login_or_register

    override fun initData() {
        arguments?.apply {
            profileViewModel.setProfileType(getInt(PROFILE_TYPE))
        }
        profileViewModel.profileType.observe(viewLifecycleOwner, Observer {
            when (it) {
                PROFILE_TYPE_LOGIN -> {
                    login_btn.text = "登录"
                    password_confirm_til.visibility = View.GONE
                }
                PROFILE_TYPE_REGISTER -> {
                    login_btn.text = "注册"
                    password_confirm_til.visibility = View.VISIBLE
                }
            }
        })
        profileViewModel.interaction.observe(viewLifecycleOwner, Observer {
            val state = it.state
            val data = it.data
            val msg = it.msg
            when (state) {
                RequestState.ERROR, RequestState.DEFAULT -> {
                    showButton()
                    msg?.apply {
                        showSnackBar(view, this)
                    }
                }
                RequestState.LOADING -> {
                    showLoading()
                }
                RequestState.SUCCESS -> {
                    val isLogin = profileViewModel.profileType.value == PROFILE_TYPE_LOGIN
                    if (isLogin) {
                        requireActivity().finish()
                    } else {
                        showSnackBar(view, "注册成功，请去登录页登录")
                        showButton()
                    }
                }
            }
        })

        login_btn.setOnClickListener {
            val isLogin = profileViewModel.profileType.value == PROFILE_TYPE_LOGIN
            val sendBean: ProfileSendBean
            if (isLogin) {
                sendBean = ProfileSendBean(
                    account_tiet.text.toString(),
                    password_tiet.text.toString(),
                    null
                )
                profileViewModel.startLogin(sendBean)
            } else {
                sendBean = ProfileSendBean(
                    account_tiet.text.toString(),
                    password_tiet.text.toString(),
                    password_confirm_tiet.text.toString()
                )
                profileViewModel.startRegister(sendBean)
            }
        }
    }


    private fun showLoading() {
        login_btn.visibility = View.INVISIBLE
        progress_bar.visibility = View.VISIBLE
    }

    private fun showButton() {
        login_btn.visibility = View.VISIBLE
        progress_bar.visibility = View.INVISIBLE
    }

    companion object {
        private const val PROFILE_TYPE = "profileType"
        fun create(profileType: Int): LoginOrRegisterFragment {
            val args = Bundle()
            args.putInt(PROFILE_TYPE, profileType)
            val fragment = LoginOrRegisterFragment()
            fragment.arguments = args
            return fragment
        }
    }
}