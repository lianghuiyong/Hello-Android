package com.xgw.androidkotlindemo.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/18 19:24
 */
@Entity
data class User(
    @PrimaryKey val id: String,
    val name: String?,
    val icon: String?
)