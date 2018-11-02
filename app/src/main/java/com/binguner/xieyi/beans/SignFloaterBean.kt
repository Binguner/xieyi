package com.binguner.xieyi.beans

data class SIgnFloaterBean(
    val code: Int,
    val `data`: List<SighFloaterData>,
    val message: String
)

data class SighFloaterData(
    val _id: String,
    val content: String,
    val created_at: String,
    val obtain_at: String,
    val region: String,
    val signatory: List<String>,
    val state: Int,
    val title: String
)