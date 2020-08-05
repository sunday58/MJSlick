package com.mjslick.model

data class LadiesWear(
    val clothName: String,
    val clothType: String,
    val clothDescription: String,
    val topPrice: String,
    val trouserPrice: String,
    val completePrice: String,
    val clothImages: ArrayList<String>

) {
    constructor(): this("", "", "", "",
    "", "", arrayListOf())
}