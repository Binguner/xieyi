package com.binguner.xieyi.beans

data class DoRegisterBean(
    val code: Int,
    val message: String,
    val data: Data
)

data class Data(
    val id: String
)