package com.twomans.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.twomans.app.data.model.AppPair
import kotlinx.coroutines.tasks.await

class PairRepository {
    private val col = FirebaseFirestore.getInstance().collection("pairs")

    suspend fun createPair(userId: String, userName: String): Result<AppPair> = runCatching {
        val ref = col.document()
        val pair = AppPair(
            id          = ref.id,
            inviteCode  = generateCode(),
            user1Id     = userId,
            user1Name   = userName
        )
        ref.set(pair).await()
        pair
    }

    suspend fun joinPair(code: String, userId: String, userName: String): Result<AppPair> = runCatching {
        val snap = col
            .whereEqualTo("inviteCode", code)
            .whereEqualTo("isComplete", false)
            .get().await()

        check(!snap.isEmpty) { "No pair found for that code." }
        val doc = snap.documents.first()
        val pair = doc.toObject(AppPair::class.java)!!
        check(pair.user1Id != userId) { "You can't join your own pair link." }

        doc.reference.update(
            mapOf("user2Id" to userId, "user2Name" to userName, "isComplete" to true)
        ).await()
        pair.copy(user2Id = userId, user2Name = userName, isComplete = true)
    }

    suspend fun getPair(pairId: String): Result<AppPair?> = runCatching {
        col.document(pairId).get().await().toObject(AppPair::class.java)
    }

    private fun generateCode(): String {
        val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
        return (1..6).map { chars.random() }.joinToString("")
    }
}
