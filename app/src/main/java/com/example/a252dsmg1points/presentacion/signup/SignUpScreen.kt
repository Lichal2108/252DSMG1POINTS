package com.example.a252dsmg1points.presentacion.signup

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a252dsmg1points.R
import com.example.a252dsmg1points.ui.theme.BackgroundButton
import com.example.a252dsmg1points.ui.theme.Black
import com.example.a252dsmg1points.ui.theme.Gray
import com.example.a252dsmg1points.ui.theme.Green
import com.example.a252dsmg1points.ui.theme.ShapeButton
import com.example.a252dsmg1points.ui.theme.White

@Composable
fun SignUpScreen(
    authViewModel: com.example.a252dsmg1points.presentation.viewmodel.AuthViewModel,
    navigationToLogin: () -> Unit = {},
    navigationToHome: () -> Unit = {},
    navigationBack: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }
    
    val authResult by authViewModel.authResult.observeAsState()
    val isLoading by authViewModel.isLoading.observeAsState(false)
    
    // Observar cambios en el resultado de autenticación
    LaunchedEffect(authResult) {
        authResult?.let { result ->
            when (result) {
                is com.example.a252dsmg1points.data.model.AuthResult.Success -> {
                    if (result.user.uid.isNotEmpty()) {
                        navigationToHome()
                    }
                }
                is com.example.a252dsmg1points.data.model.AuthResult.Error -> {
                    errorMessage = result.message
                }
                else -> {}
            }
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with back button
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
                    text = "Crear Cuenta",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.width(40.dp))
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Logo
            Card(
                modifier = Modifier
                    .size(80.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Sign up form
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
                        text = "¡Únete a nosotros!",
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Crea tu cuenta para comenzar",
                        color = Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Full name field
                    CustomTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = "Nombre completo",
                        leadingIcon = Icons.Default.Person,
                        keyboardType = KeyboardType.Text
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Email field
                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Correo electrónico",
                        leadingIcon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Phone field
                    CustomTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Teléfono",
                        leadingIcon = Icons.Default.Phone,
                        keyboardType = KeyboardType.Phone
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Password field
                    CustomTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onPasswordVisibilityToggle = { passwordVisible = !passwordVisible }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Confirm password field
                    CustomTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "Confirmar contraseña",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        passwordVisible = confirmPasswordVisible,
                        onPasswordVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Terms and conditions
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        androidx.compose.material3.Checkbox(
                            checked = acceptTerms,
                            onCheckedChange = { acceptTerms = it },
                            colors = androidx.compose.material3.CheckboxDefaults.colors(
                                checkedColor = Green,
                                uncheckedColor = Gray
                            )
                        )
                        Text(
                            text = "Acepto los términos y condiciones",
                            color = White,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { acceptTerms = !acceptTerms }
                        )
                    }
                    
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
                    
                    // Sign up button
                    Button(
                        onClick = {
                            if (validateForm(fullName, email, phone, password, confirmPassword, acceptTerms)) {
                                if (password == confirmPassword) {
                                    errorMessage = ""
                                    authViewModel.signUp(email, password, fullName, phone)
                                } else {
                                    errorMessage = "Las contraseñas no coinciden"
                                }
                            } else {
                                errorMessage = "Por favor completa todos los campos y acepta los términos"
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
                            text = if (isLoading) "Creando cuenta..." else "Crear Cuenta",
                            color = Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Login link
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "¿Ya tienes cuenta? ",
                            color = White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Inicia sesión",
                            color = Green,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { navigationToLogin() }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityToggle: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = White) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = Green
            )
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { onPasswordVisibilityToggle?.invoke() }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = Green
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
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
}

private fun validateForm(
    fullName: String,
    email: String,
    phone: String,
    password: String,
    confirmPassword: String,
    acceptTerms: Boolean
): Boolean {
    return fullName.isNotEmpty() && 
           email.isNotEmpty() && 
           phone.isNotEmpty() && 
           password.isNotEmpty() && 
           confirmPassword.isNotEmpty() && 
           acceptTerms
}