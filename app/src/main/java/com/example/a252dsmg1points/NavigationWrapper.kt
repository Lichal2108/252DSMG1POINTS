package com.example.a252dsmg1points
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.unit.sp
import com.example.a252dsmg1points.presentacion.initial.InitialScreen
import com.example.a252dsmg1points.presentacion.login.LogInScreen
import com.example.a252dsmg1points.presentacion.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth
){
    NavHost(navController=navHostController, startDestination = "initial") {
        composable(route="initial"){
            InitialScreen(
                navigationToLogin = { navHostController.navigate(route="logIn") },
                navigationToSignUp = { navHostController.navigate(route="signUp") }
            )
        }
        composable(route="logIn"){
            LogInScreen(
                auth = auth,
                navigationToSignUp = { navHostController.navigate(route="signUp") },
                navigationToHome = { navHostController.navigate(route="home") },
                navigationBack = { navHostController.popBackStack() }
            )
        }
        composable(route="signUp"){
            SignUpScreen(
                auth = auth,
                navigationToLogin = { navHostController.navigate(route="logIn") },
                navigationToHome = { navHostController.navigate(route="home") },
                navigationBack = { navHostController.popBackStack() }
            )
        }
        composable(route="home"){
            // TODO: Implement HomeScreen
            androidx.compose.material3.Surface(
                modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                color = com.example.a252dsmg1points.ui.theme.Black
            ) {
                androidx.compose.foundation.layout.Box(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text(
                        text = "¡Bienvenido a la aplicación!",
                        color = com.example.a252dsmg1points.ui.theme.White,
                        fontSize = 24.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                }
            }
        }
    }
}