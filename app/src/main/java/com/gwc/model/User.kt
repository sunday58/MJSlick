package com.gwc.model

data class User(
    val email: String,
    val phone: String,
    val twitter: String,
    val facebook: String,
    val linkedin: String
) {
    constructor(): this("", "", "", "", "")
}