package com.example.a252dsmg1points.presentacion.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
fun InitialScreen(
    navigationToLogin: () -> Unit = {},
    navigationToSignUp: () -> Unit = {}
) {
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
            Spacer(modifier = Modifier.height(80.dp))
            
            // Logo with enhanced visibility
            Card(
                modifier = Modifier
                    .size(120.dp)
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
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Enhanced title section
            Text(
                text = "Descubre",
                color = White,
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "los mejores Lugares",
                color = Green,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Grupo 3",
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(60.dp))
            
            // Enhanced buttons section
            Button(
                onClick = navigationToSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Green),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Crear Cuenta Gratis",
                    color = Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            CustomButton(
                modifier = Modifier.clickable { /* Google Sign In */ },
                painter = painterResource(id = R.drawable.google),
                title = "Continuar con Google"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            CustomButton(
                modifier = Modifier.clickable { /* Facebook Sign In */ },
                painter = painterResource(id = R.drawable.facebook),
                title = "Continuar con Facebook"
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "¿Ya tienes cuenta?",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Iniciar Sesión",
                color = Green,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { navigationToLogin() }
                    .padding(16.dp)
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = BackgroundButton),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundButton)
                .border(1.dp, ShapeButton, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(20.dp)
            )
            Text(
                text = title,
                color = White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}