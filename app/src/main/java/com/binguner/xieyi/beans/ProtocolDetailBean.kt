package com.binguner.xieyi.beans


data class ProtocolDetailBean(
    val _id: String,
    val owner:String,
    val title: String,
    val content: String,
    val signatoryNum: String?,
    var signatory: List<String>?,
    val created_at: String,
    val obtain_at: String?,
    val state: String?,
    val region: String?,
    val share: String?,
    val comments: List<Any>?,
    val protocol_praise: List<Any>?,
    val type: Int   // 0 normal     1 floater
)