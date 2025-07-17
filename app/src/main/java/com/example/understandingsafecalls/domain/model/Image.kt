package com.example.understandingsafecalls.domain.model

data class Image(
    val id: Int,
    val imageUrl: String,
    val tags: String,
    val user: String,
    val userImageUrl: String,
    val likes: Int,
    val comments: Int
)