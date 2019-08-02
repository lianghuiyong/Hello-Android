package com.teemo.hello.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/19 16:17
 */
@Dao
interface CookieDao {
    @Query("SELECT * FROM cookie")
    fun loadCookies(): LiveData<List<Cookie>?>

    @Query("SELECT * FROM cookie ORDER BY expiresAt LIMIT 1")
    fun getLatestExpireCookie() : LiveData<Cookie?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceCookies(cookies: List<Cookie>)

    @Query("DELETE FROM cookie")
    fun clearAllCookies()
}