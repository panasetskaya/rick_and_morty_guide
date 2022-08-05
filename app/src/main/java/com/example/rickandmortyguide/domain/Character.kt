package com.example.rickandmortyguide.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String? = null,
    val gender: String? = null,
    val status: String? = null,
    val species: String? = null,
    val created: String? = null,
    val image: String? = null
): Parcelable
