package com.example.a252dsmg1points.presentacion.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.a252dsmg1points.data.model.User
import com.example.a252dsmg1points.presentation.viewmodel.AuthViewModel
import com.example.a252dsmg1points.ui.theme.BackgroundButton
import com.example.a252dsmg1points.ui.theme.Black
import com.example.a252dsmg1points.ui.theme.Gray
import com.example.a252dsmg1points.ui.theme.Green
import com.example.a252dsmg1points.ui.theme.ShapeButton
import com.example.a252dsmg1points.ui.theme.White

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    navigationBack: () -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    
    val currentUser by authViewModel.currentUser.observeAsState()
    val authResult by authViewModel.authResult.observeAsState()
    val isLoading by authViewModel.isLoading.observeAsState(false)
    
    // Launcher para seleccionar imagen
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            authViewModel.uploadProfileImage(it)
        }
    }
    
    // Observar cambios en el usuario actual
    LaunchedEffect(Unit) {
        authViewModel.checkCurrentUser()
    }
    
    // Actualizar campos cuando cambie el usuario
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            fullName = user.fullName
            phone = user.phone
        }
    }
    
    // Observar resultados de autenticación
    LaunchedEffect(authResult) {
        when (authResult) {
            is com.example.a252dsmg1points.data.model.AuthResult.Error -> {
                errorMessage = authResult.message
            }
            else -> {}
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(Gray, Black),
                startY = 0f,
                endY = 1000f
            ))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = navigationBack,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = BackgroundButton,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
                
                Text(
                    text = "Mi Perfil",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = { isEditing = !isEditing },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isEditing) Green else BackgroundButton,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = White
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Profile Image
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = BackgroundButton)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (currentUser?.profileImageUrl?.isNotEmpty() == true) {
                        AsyncImage(
                            model = currentUser?.profileImageUrl,
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Image",
                            tint = White,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    
                    // Camera button overlay
                    if (isEditing) {
                        Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = Green,
                                shape = CircleShape
                            )
                            .clickable { imagePickerLauncher.launch("image/*") }
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change Photo",
                            tint = Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // User Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = BackgroundButton),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Información Personal",
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Email (read-only)
                    OutlinedTextField(
                        value = currentUser?.email ?: "",
                        onValueChange = { },
                        label = { Text("Email", color = White) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = Green
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = Gray,
                            disabledBorderColor = ShapeButton,
                            disabledLabelColor = Gray
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Full Name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Nombre completo", color = White) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Green
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isEditing,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedBorderColor = Green,
                            unfocusedBorderColor = ShapeButton,
                            focusedLabelColor = Green,
                            unfocusedLabelColor = Gray,
                            cursorColor = Green
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Phone
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Teléfono", color = White) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Green
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isEditing,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedBorderColor = Green,
                            unfocusedBorderColor = ShapeButton,
                            focusedLabelColor = Green,
                            unfocusedLabelColor = Gray,
                            cursorColor = Green
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Error message
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    // Save button
                    if (isEditing) {
                        Button(
                            onClick = {
                                currentUser?.let { user ->
                                    val updatedUser = user.copy(
                                        fullName = fullName,
                                        phone = phone
                                    )
                                    authViewModel.updateUserProfile(updatedUser)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Green),
                            shape = RoundedCornerShape(12.dp),
                            enabled = !isLoading
                        ) {
                            Text(
                                text = if (isLoading) "Guardando..." else "Guardar Cambios",
                                color = Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Sign out button
                    TextButton(
                        onClick = {
                            authViewModel.signOut()
                            navigationBack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Cerrar Sesión",
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
