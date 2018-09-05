package com.binguner.xieyi.beans


data class DoProtocolBean(
    val code: Int,
    val message: String,
    val data: Data3
)

data class Data3(
    val id: String
)