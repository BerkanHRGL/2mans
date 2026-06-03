package com.twomans.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AppUser(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val pairId: String? = null
)
