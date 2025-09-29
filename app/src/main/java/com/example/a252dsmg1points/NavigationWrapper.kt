package com.example.a252dsmg1points
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.a252dsmg1points.presentacion.initial.InitialScreen
import com.example.a252dsmg1points.presentacion.login.LogInScreen
import com.example.a252dsmg1points.presentacion.signup.SignUpScreen
import com.example.a252dsmg1points.presentacion.profile.ProfileScreen
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
            val authViewModel: com.example.a252dsmg1points.presentation.viewmodel.AuthViewModel = hiltViewModel()
            LogInScreen(
                authViewModel = authViewModel,
                navigationToSignUp = { navHostController.navigate(route="signUp") },
                navigationToHome = { navHostController.navigate(route="home") },
                navigationBack = { navHostController.popBackStack() }
            )
        }
        composable(route="signUp"){
            val authViewModel: com.example.a252dsmg1points.presentation.viewmodel.AuthViewModel = hiltViewModel()
            SignUpScreen(
                authViewModel = authViewModel,
                navigationToLogin = { navHostController.navigate(route="logIn") },
                navigationToHome = { navHostController.navigate(route="home") },
                navigationBack = { navHostController.popBackStack() }
            )
        }
        composable(route="home"){
            val authViewModel: com.example.a252dsmg1points.presentation.viewmodel.AuthViewModel = hiltViewModel()
            ProfileScreen(
                authViewModel = authViewModel,
                navigationBack = { navHostController.popBackStack() }
            )
        }
    }
}