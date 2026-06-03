package com.twomans.app.data.repository

import com.twomans.app.data.model.AppPair
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class PairRepository {
    private val col = Firebase.firestore.collection("pairs")

    suspend fun createPair(userId: String, userName: String): Result<AppPair> = runCatching {
        val ref = col.document            // new document reference with a generated id
        val pair = AppPair(
            id         = ref.id,
            inviteCode = generateCode(),
            user1Id    = userId,
            user1Name  = userName
        )
        ref.set(pair)
        pair
    }

    suspend fun joinPair(code: String, userId: String, userName: String): Result<AppPair> = runCatching {
        val snap = col.where {
            ("inviteCode" equalTo code) and ("isComplete" equalTo false)
        }.get()

        check(snap.documents.isNotEmpty()) { "No pair found for that code." }
        val doc = snap.documents.first()
        val pair = doc.data<AppPair>()
        check(pair.user1Id != userId) { "You can't join your own pair link." }

        doc.reference.update(
            "user2Id" to userId,
            "user2Name" to userName,
            "isComplete" to true
        )
        pair.copy(user2Id = userId, user2Name = userName, isComplete = true)
    }

    suspend fun getPair(pairId: String): Result<AppPair?> = runCatching {
        val snap = col.document(pairId).get()
        if (snap.exists) snap.data<AppPair>() else null
    }

    private fun generateCode(): String {
        val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
        return (1..6).map { chars.random() }.joinToString("")
    }
}
