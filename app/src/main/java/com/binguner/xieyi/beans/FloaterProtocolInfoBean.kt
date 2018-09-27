package com.binguner.xieyi.beans


data class FloaterProtocolInfoBean(
    val code: Int,
    val message: String,
    val data: Data6
)

data class Data6(
    val _id: String,
    val title: String,
    val content: String,
    val signatory: List<String>?,
    val created_at: String,
    val obtain_at: String?,
    val region: String,
    val state: String
)