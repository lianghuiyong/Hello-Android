package com.xgw.androidkotlindemo.api

import android.util.Log
import com.xgw.androidkotlindemo.app.App
import com.xgw.androidkotlindemo.base.BaseResponse
import com.xgw.androidkotlindemo.bean.response.BannerResponse
import com.xgw.androidkotlindemo.bean.response.ProfileResponse
import com.xgw.androidkotlindemo.bean.response.WxArticleResponse
import com.xgw.androidkotlindemo.bean.response.WxAuthorResponse
import com.xgw.androidkotlindemo.data.AppDatabase
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/17 11:59
 */
interface NetworkApi {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
        private const val TAG = "NetworkApi"
        private const val TIMEOUT_SECONDS = 60L

        @Volatile
        private var networkApi: NetworkApi? = null

        fun create(baseUrl: String = BASE_URL): NetworkApi =
            networkApi ?: synchronized(this) {
                networkApi ?: {
                    //日志拦截
                    val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                        Log.d(TAG, it)
                    })
                    //日志截取段
                    logger.level = HttpLoggingInterceptor.Level.BODY

                    //okhttp client
                    val client = OkHttpClient.Builder()
                        .cookieJar(object : CookieJar {
                            val cookieDao = AppDatabase.getInstance(App.instance.applicationContext)
                                .cookieDao()

                            override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                                val savedCookies = mutableListOf<com.xgw.androidkotlindemo.data.Cookie>()
                                cookies.forEach {
                                    savedCookies.add(
                                        com.xgw.androidkotlindemo.data.Cookie(
                                            it.name(),
                                            it.value(),
                                            it.expiresAt(),
                                            it.domain(),
                                            it.path(),
                                            it.secure(),
                                            it.httpOnly(),
                                            it.persistent(),
                                            it.hostOnly()
                                        )
                                    )
                                }
                                cookieDao.clearAllCookies()
                                cookieDao.replaceCookies(savedCookies)
                            }

                            override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                                val savedCookies = cookieDao.loadCookies()
                                val cookies = mutableListOf<Cookie>()
                                savedCookies.value?.forEach {
                                    cookies.add(
                                        Cookie.Builder()
                                            .name(it.name)
                                            .value(it.value)
                                            .expiresAt(it.expiresAt)
                                            .domain(it.domain)
                                            .path(it.path)
                                            .build()
                                    )
                                }
                                return cookies
                            }

                        })
                        .addInterceptor(logger)
                        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                        .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                        .build()
                    //retrofit create api
                    Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(NetworkApi::class.java).also {
                            networkApi = it
                        }
                }.invoke()
                    .also {
                        networkApi = it
                    }
            }
    }

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") password: String): BaseResponse<ProfileResponse>?

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String, @Field("password") password: String, @Field("repassword") confirmPassword: String): BaseResponse<ProfileResponse>?

    /**
     * 获取banner
     */
    @GET("/banner/json")
    suspend fun getBanner(): BaseResponse<List<BannerResponse>>?

    /**
     * 获取微信公众号作者列表
     */
    @GET("/wxarticle/chapters/json")
    suspend fun getWxAuthors(): BaseResponse<List<WxAuthorResponse>>?

    /**
     * 获取某个微信公众号下的文章列表
     */
    @GET("/wxartical/list/{pageNo}/{wxid}/json")
    suspend fun getWxArticales(@Path("pageNo") pageNo: Int, @Path("wxid") wxid: String): BaseResponse<List<WxArticleResponse>>?
}