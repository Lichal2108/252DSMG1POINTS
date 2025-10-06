package com.example.a252dsmg1points.data.repository

import android.net.Uri
import com.example.a252dsmg1points.data.model.AuthResult
import com.example.a252dsmg1points.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class AuthRepository @Inject constructor(
    protected val auth: FirebaseAuth,
    protected val firestore: FirebaseFirestore,
    protected val storage: FirebaseStorage
) {
    
    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                val userData = getUserData(user.uid)
                AuthResult.Success(userData)
            } else {
                AuthResult.Error("Error al iniciar sesión")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Error desconocido")
        }
    }
    
    suspend fun createUserWithEmailAndPassword(
        email: String, 
        password: String, 
        fullName: String, 
        phone: String
    ): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                // Crear perfil de usuario en Firestore
                val userData = User(
                    uid = user.uid,
                    email = email,
                    fullName = fullName,
                    phone = phone,
                    isEmailVerified = user.isEmailVerified
                )
                saveUserData(userData)
                AuthResult.Success(userData)
            } else {
                AuthResult.Error("Error al crear la cuenta")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Error desconocido")
        }
    }
    
    suspend fun uploadProfileImage(uid: String, imageUri: Uri): String? {
        return try {
            val storageRef = storage.reference.child("profile_images/$uid.jpg")
            val uploadTask = storageRef.putFile(imageUri).await()
            val downloadUrl = storageRef.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun updateUserProfile(user: User): AuthResult {
        return try {
            saveUserData(user)
            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Error al actualizar perfil")
        }
    }
    
    suspend fun getUserData(uid: String): User {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            document.toObject(User::class.java) ?: User(uid = uid)
        } catch (e: Exception) {
            User(uid = uid)
        }
    }
    
    private suspend fun saveUserData(user: User) {
        firestore.collection("users").document(user.uid).set(user).await()
    }
    
    fun signOut() {
        auth.signOut()
    }
    
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
    
    suspend fun sendPasswordResetEmail(email: String): AuthResult {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthResult.Success(User()) // No necesitamos datos del usuario aquí
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Error al enviar email de recuperación")
        }
    }
}
