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
    val protocols: List<Protocol>,
    val protocol: List<String>,
    val floater: List<String>,
    val nickname: String,
    val sex: String,
    val email: String,
    val avatar: String,
    val career: String,
    val region: String
)

data class Protocol(
    val id: String,
    val type: Int
)