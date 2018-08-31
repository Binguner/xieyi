package com.binguner.xieyi.beans



data class DoLoginBean(
    val code: Int,
    val message: String,
    val data: Data2
)

data class Data2(
    val _id: String,
    val username: String,
    val phone: String,
    val password: String,
    val protocols: List<String>,
    val nickname: Any,
    val sex: Any,
    val email: String
)