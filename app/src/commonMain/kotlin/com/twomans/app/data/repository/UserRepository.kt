package com.twomans.app.data.repository

import com.twomans.app.data.model.AppUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class UserRepository {
    private val col = Firebase.firestore.collection("users")

    suspend fun createUser(user: AppUser): Result<Unit> = runCatching {
        col.document(user.uid).set(user)
    }

    suspend fun getUser(uid: String): Result<AppUser?> = runCatching {
        val snap = col.document(uid).get()
        if (snap.exists) snap.data<AppUser>() else null
    }

    suspend fun updatePairId(uid: String, pairId: String): Result<Unit> = runCatching {
        col.document(uid).update("pairId" to pairId)
    }
}
