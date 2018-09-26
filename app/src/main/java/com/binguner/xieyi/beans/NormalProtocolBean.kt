package com.binguner.xieyi.beans


data class NormalProtocolBean(
    val code: Int,
    val message: String,
    val data: Data
)

data class Data(
    val _id: String,
    val title: String,
    val content: String,
    val signatoryNum: Int,
    val signatory: List<String>,
    val created_at: String,
    val state: Int,
    val share: Int,
    val comments: List<Any>,
    val protocol_praise: List<Any>
)