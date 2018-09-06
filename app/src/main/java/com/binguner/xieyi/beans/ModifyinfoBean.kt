package com.binguner.xieyi.beans


data class ModifyinfoBean(
    val code: Int,
    val message: String,
    val data: Data5
)

data class Data5(
    val n: Int,
    val nModified: Int,
    val ok: Int
)