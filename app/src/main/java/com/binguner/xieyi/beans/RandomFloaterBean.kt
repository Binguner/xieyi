package com.binguner.xieyi.beans

data class RandomFloaterBean(
    val code: Int,
    val data: RandomFloaterData,
    val message: String
)

data class RandomFloaterData(
    val _id: String,
    val content: String,
    val created_at: String,
    val obtain_at: String,
    val region: String,
    val signatory: List<String>,
    val state: Int,
    val title: String
)