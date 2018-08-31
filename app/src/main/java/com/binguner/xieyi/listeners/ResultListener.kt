package com.binguner.xieyi.listeners

interface ResultListener{
    companion object {
        val errorType = 0
        val succeedType = 1
        val failedType = 2
        val nextType = 3
    }
    fun postResullt(resultType:Int, msg: String)
}