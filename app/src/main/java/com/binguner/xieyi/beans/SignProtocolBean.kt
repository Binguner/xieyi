package com.binguner.xieyi.beans

data class SignProtocolBean(
    val code: Int,
    val `data`: List<SignProtocolData>,
    val message: String
)

data class SignProtocolData(
    val _id: String,
    val comments: List<Any>,
    val content: String,
    val created_at: String,
    val protocol_praise: List<Any>,
    val share: Int,
    val signatory: List<String>,
    val signatoryNum: Int,
    val state: Int,
    val title: String
)