package com.mjslick.model

import java.io.Serializable

data class MaleWear(
    val clothName: String,
    val clothType: String,
    val clothDescription: String,
    val topPrice: String,
    val trouserPrice: String,
    val completePrice: String,
    val clothImages: List<String>
): Serializable {
    constructor(): this("", "", "", "",
        "", "", listOf())
}