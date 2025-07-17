package com.example.understandingsafecalls.data.remote.mapper

import com.example.understandingsafecalls.data.remote.dto.Hits
import com.example.understandingsafecalls.data.remote.dto.PhotoResponse
import com.example.understandingsafecalls.domain.model.Image
import com.example.understandingsafecalls.domain.model.ImageList

fun PhotoResponse.toDomain(): ImageList {
    return ImageList(
        total = total ?: 0,
        totalHits = totalHits ?: 0,
        images = hits.mapNotNull { it.toDomain() }
    )
}

fun Hits.toDomain(): Image? {
    val id = id ?: return null
    val imageUrl = largeImageURL ?: webformatURL ?: previewURL ?: return null
    val user = user ?: return null
    val tags = tags ?: ""
    val likes = likes ?: 0
    val comments = comments ?: 0
    val userImageUrl = userImageURL ?: ""

    return Image(
        id = id,
        imageUrl = imageUrl,
        tags = tags,
        user = user,
        userImageUrl = userImageUrl,
        likes = likes,
        comments = comments
    )
}
