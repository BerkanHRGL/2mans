package com.twomans.app.data.model

import com.google.firebase.firestore.DocumentId

data class AppPair(
    @DocumentId val id: String = "",
    val inviteCode: String = "",
    val user1Id: String = "",
    val user1Name: String = "",
    val user2Id: String? = null,
    val user2Name: String? = null,
    val isComplete: Boolean = false
)
