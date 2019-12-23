package com.reactlibrary.types

data class Drop(
        val cx: Float,
        val cy: Float,
        var radius: Float,
        var isWaning: Boolean = false
)