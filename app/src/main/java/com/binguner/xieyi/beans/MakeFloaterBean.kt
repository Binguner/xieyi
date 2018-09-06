package com.binguner.xieyi.beans

data class MakeFloaterBean(
    val code: Int,
    val message: String,
    val data: Data4
)

data class Data4(
    val id: String
)