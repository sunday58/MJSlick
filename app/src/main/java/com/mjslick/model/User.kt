package com.mjslick.model

data class User(
    val email: String,
    val phone: String,
    val twitter: String,
    val facebook: String,
    val linkedin: String,
    val ladiesWear: List<LadiesWear>,
    val maleWear: List<MaleWear>
) {
    constructor(): this("", "", "", "", "", listOf(), listOf())
}