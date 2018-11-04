package com.binguner.xieyi.beans

data class ProtocolListBean(
    val code: Int,
    val `data`: List<ProtocolListBeanData>,
    val message: String
)

data class ProtocolListBeanData(
    val _id: String,
    val comments: List<Any>?,
    val content: String,
    val created_at: String,
    val protocol_praise: List<Any>?,
    val share: String,
    val signatory: List<String>,
    val signatoryNum: String,
    val state: String,
    val title: String
)