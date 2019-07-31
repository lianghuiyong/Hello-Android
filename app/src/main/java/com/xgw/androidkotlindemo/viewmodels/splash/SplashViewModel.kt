package com.xgw.androidkotlindemo.viewmodels.splash
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xgw.androidkotlindemo.base.BaseViewModel
import kotlinx.coroutines.*

class SplashViewModel : BaseViewModel<Any>() {
    val delayTask = MutableLiveData<Int>()

    fun startDelay() {
        viewModelScope.launch {
            println("执行主线程当前线程:${Thread.currentThread().name}")
            var count = 4
            repeat(3) {
                println("开始进入，第$it 次，当前线程：${Thread.currentThread().name}")
                count -= 1
                delayTask.value = count
                delay(1000)
            }
        }
    }
}