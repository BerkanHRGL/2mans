package com.twomans.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twomans.app.data.model.AppPair
import com.twomans.app.data.model.AppUser
import com.twomans.app.data.repository.AuthRepository
import com.twomans.app.data.repository.PairRepository
import com.twomans.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    private val authRepo = AuthRepository()
    private val userRepo = UserRepository()
    private val pairRepo = PairRepository()

    sealed class AuthState {
        object Loading : AuthState()
        object Unauthenticated : AuthState()
        data class Authenticated(val user: AppUser, val hasPair: Boolean) : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState = _authState.asStateFlow()

    private val _currentPair = MutableStateFlow<AppPair?>(null)
    val currentPair = _currentPair.asStateFlow()

    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init { checkAuth() }

    private fun checkAuth() {
        viewModelScope.launch {
            val firebaseUser = authRepo.currentUser
            if (firebaseUser == null) { _authState.value = AuthState.Unauthenticated; return@launch }
            val user = userRepo.getUser(firebaseUser.uid).getOrNull()
            if (user == null) { authRepo.signOut(); _authState.value = AuthState.Unauthenticated; return@launch }
            if (user.pairId != null) _currentPair.value = pairRepo.getPair(user.pairId).getOrNull()
            _authState.value = AuthState.Authenticated(user, user.pairId != null)
        }
    }

    fun signUp(name: String, email: String, password: String, onDone: (hasPair: Boolean) -> Unit) {
        viewModelScope.launch {
            isLoading = true; error = null
            val fbUser = authRepo.signUp(email, password)
                .onFailure { error = it.message; isLoading = false; return@launch }
                .getOrNull()!!
            userRepo.createUser(AppUser(uid = fbUser.uid, name = name, email = email))
                .onFailure { error = "Failed to save profile"; isLoading = false; return@launch }
            val user = AppUser(uid = fbUser.uid, name = name, email = email)
            _authState.value = AuthState.Authenticated(user, false)
            isLoading = false
            onDone(false)
        }
    }

    fun signIn(email: String, password: String, onDone: (hasPair: Boolean) -> Unit) {
        viewModelScope.launch {
            isLoading = true; error = null
            val fbUser = authRepo.signIn(email, password)
                .onFailure { error = it.message; isLoading = false; return@launch }
                .getOrNull()!!
            val user = userRepo.getUser(fbUser.uid).getOrNull()
            if (user == null) { error = "Account not found"; isLoading = false; return@launch }
            if (user.pairId != null) _currentPair.value = pairRepo.getPair(user.pairId).getOrNull()
            _authState.value = AuthState.Authenticated(user, user.pairId != null)
            isLoading = false
            onDone(user.pairId != null)
        }
    }

    fun createPair(onDone: () -> Unit = {}) {
        val state = _authState.value as? AuthState.Authenticated ?: return
        if (_currentPair.value != null) { onDone(); return }
        viewModelScope.launch {
            isLoading = true; error = null
            val pair = pairRepo.createPair(state.user.uid, state.user.name)
                .onFailure { error = "Failed to create pair"; isLoading = false; return@launch }
                .getOrNull()!!
            userRepo.updatePairId(state.user.uid, pair.id)
            _currentPair.value = pair
            _authState.value = AuthState.Authenticated(state.user.copy(pairId = pair.id), true)
            isLoading = false
            onDone()
        }
    }

    fun joinPair(code: String, onDone: () -> Unit) {
        val state = _authState.value as? AuthState.Authenticated ?: return
        viewModelScope.launch {
            isLoading = true; error = null
            val pair = pairRepo.joinPair(code.trim().uppercase(), state.user.uid, state.user.name)
                .onFailure { error = it.message ?: "Invalid code"; isLoading = false; return@launch }
                .getOrNull()!!
            userRepo.updatePairId(state.user.uid, pair.id)
            _currentPair.value = pair
            _authState.value = AuthState.Authenticated(state.user.copy(pairId = pair.id), true)
            isLoading = false
            onDone()
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepo.signOut()
            _authState.value = AuthState.Unauthenticated
            _currentPair.value = null
        }
    }

    fun clearError() { error = null }
}
