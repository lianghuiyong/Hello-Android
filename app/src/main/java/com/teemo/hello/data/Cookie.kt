package com.teemo.hello.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/19 16:10
 */
@Entity
data class Cookie constructor(
    @PrimaryKey val name: String,
    val value: String,
    val expiresAt: Long,
    val domain: String,
    val path: String,
    val secure: Boolean,
    val httpOnly: Boolean,
    val persistent: Boolean,
    val hostOnly: Boolean
)