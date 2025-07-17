package com.example.understandingsafecalls.domain.model

data class ImageList(
    val total: Int,
    val totalHits: Int,
    val images: List<Image>
)