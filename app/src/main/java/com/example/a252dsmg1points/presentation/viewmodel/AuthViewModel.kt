package com.example.a252dsmg1points.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a252dsmg1points.data.model.AuthResult
import com.example.a252dsmg1points.data.model.User
import com.example.a252dsmg1points.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    protected val authRepository: AuthRepository
) : ViewModel() {
    protected val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser
    
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _authResult.value = AuthResult.Loading
            
            val result = authRepository.signInWithEmailAndPassword(email, password)
            _authResult.value = result
            _isLoading.value = false
            
            if (result is AuthResult.Success) {
                _currentUser.value = result.user
            }
        }
    }
    
    fun signUp(email: String, password: String, fullName: String, phone: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _authResult.value = AuthResult.Loading
            
            val result = authRepository.createUserWithEmailAndPassword(email, password, fullName, phone)
            _authResult.value = result
            _isLoading.value = false
            
            if (result is AuthResult.Success) {
                _currentUser.value = result.user
            }
        }
    }
    
    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            val currentUser = _currentUser.value
            if (currentUser != null) {
                _isLoading.value = true
                val imageUrl = authRepository.uploadProfileImage(currentUser.uid, imageUri)
                if (imageUrl != null) {
                    val updatedUser = currentUser.copy(profileImageUrl = imageUrl)
                    updateUserProfile(updatedUser)
                }
                _isLoading.value = false
            }
        }
    }
    
    fun updateUserProfile(user: User) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.updateUserProfile(user)
            _authResult.value = result
            _isLoading.value = false
            
            if (result is AuthResult.Success) {
                _currentUser.value = result.user
            }
        }
    }
    
    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.sendPasswordResetEmail(email)
            _authResult.value = result
            _isLoading.value = false
        }
    }
    
    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
        _authResult.value = AuthResult.Success(User())
    }
    
    fun checkCurrentUser() {
        val firebaseUser = authRepository.getCurrentUser()
        if (firebaseUser != null) {
            viewModelScope.launch {
                val user = authRepository.getUserData(firebaseUser.uid)
                _currentUser.value = user
            }
        }
    }
    
    fun clearAuthResult() {
        _authResult.value = AuthResult.Success(User())
    }
}
