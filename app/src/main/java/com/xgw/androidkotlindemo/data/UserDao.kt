package com.xgw.androidkotlindemo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/18 19:49
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): LiveData<List<User>?>

    @Query("SELECT * FROM user ORDER BY id LIMIT 1")
    fun getFirstUser() : LiveData<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("DELETE FROM user")
    fun deleteAllUsers()
}