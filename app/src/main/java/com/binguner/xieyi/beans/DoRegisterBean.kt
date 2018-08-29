package com.binguner.xieyi.beans

data class DoRegisterBean(
    val code: Int,
    val message: String,
    val data: Data1
)

data class Data1(
    val id: String
)