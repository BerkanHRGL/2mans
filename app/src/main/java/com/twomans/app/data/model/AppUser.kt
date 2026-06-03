package com.twomans.app.data.model

import com.google.firebase.firestore.DocumentId

data class AppUser(
    @DocumentId val uid: String = "",
    val name: String = "",
    val email: String = "",
    val pairId: String? = null
)
