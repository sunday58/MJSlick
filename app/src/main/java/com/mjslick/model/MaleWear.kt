package com.mjslick.model

data class MaleWear(
    val clothName: String,
    val clothType: String,
    val clothDescription: String,
    val topPrice: String,
    val trouserPrice: String,
    val completePrice: String,
    val clothImages: List<String>
) {
    constructor(): this("", "", "", "",
        "", "", listOf())
}