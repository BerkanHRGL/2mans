package com.twomans.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.twomans.app.data.model.AppUser
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val col = FirebaseFirestore.getInstance().collection("users")

    suspend fun createUser(user: AppUser): Result<Unit> = runCatching {
        col.document(user.uid).set(user).await()
    }

    suspend fun getUser(uid: String): Result<AppUser?> = runCatching {
        col.document(uid).get().await().toObject(AppUser::class.java)
    }

    suspend fun updatePairId(uid: String, pairId: String): Result<Unit> = runCatching {
        col.document(uid).update("pairId", pairId).await()
    }
}
