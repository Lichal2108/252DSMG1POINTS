package com.example.a252dsmg1points
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
//                navigationToLogin={navHostController.navigate(route="logIn")},
//                navigationToSignUp={navHostController.navigate(route="signUp")}
            )
        }
        composable(route="logIn"){
            LogInScreen(
//                auth,
//                navigationToSignUp={navHostController.navigate(route="signUp")}

            )
        }
        composable(route="signUp"){
            SignUpScreen()
        }
        composable(route="home"){
            //pendiente llamar a HomeScreen
        }
    }

}