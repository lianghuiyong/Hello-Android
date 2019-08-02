package com.teemo.hello.data

import com.teemo.hello.api.NetworkApi

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/19 17:55
 */
class MainRepository private constructor(
    private val userDao: UserDao,
    private val cookieDao: CookieDao
) {
    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(userDao: UserDao, cookieDao: CookieDao): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(userDao, cookieDao).also {
                    instance = it
                }
            }
    }

    fun getCookies() = cookieDao.loadCookies()

    fun getLatestExpireCookie() = cookieDao.getLatestExpireCookie()

    suspend fun getBanners() = NetworkApi.create().getBanner()

    suspend fun getWxAuthors() = NetworkApi.create().getWxAuthors()
}