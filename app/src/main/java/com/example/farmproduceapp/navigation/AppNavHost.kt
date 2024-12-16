package com.example.farmproduceapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmproduceapp.data.ProductViewModel
import com.example.farmproduceapp.ui.theme.screens.SplashScreen
import com.example.farmproduceapp.ui.theme.screens.farmer.AddFarmerScreen
import com.example.farmproduceapp.ui.theme.screens.farmer.UpdateFarmerScreen
import com.example.farmproduceapp.ui.theme.screens.farmer.ViewFarmers
import com.example.farmproduceapp.ui.theme.screens.dashboard.FarmerDashboard
import com.example.farmproduceapp.ui.theme.screens.dashboard.ProductDashboard
import com.example.farmproduceapp.ui.theme.screens.login.LoginScreen
import com.example.farmproduceapp.ui.theme.screens.products.AddProductScreen
import com.example.farmproduceapp.ui.theme.screens.products.UpdateProductScreen
import com.example.farmproduceapp.ui.theme.screens.products.ViewProduce
import com.example.myprecioustraditionalattires.ui.theme.screens.PaymentScreen
import com.example.wemahostels.ui.theme.screens.signup.SignupScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(ROUTE_SPLASH) {
            SplashScreen {
                navController.navigate(ROUTE_REGISTER) {
                    popUpTo(ROUTE_SPLASH) { inclusive = true }
                }
            }
        }

        // Authentication Screens
        composable(ROUTE_REGISTER) { SignupScreen(navController) }
        composable(ROUTE_LOGIN) { LoginScreen(navController) }

        // Dashboards
        composable(ROUTE_HOME_ONE) { FarmerDashboard(navController) }
        composable(ROUTE_HOME_TWO) {
            val productViewModel: ProductViewModel = viewModel()
            ProductDashboard(navController = navController)
        }

        // Farmer Screens
        composable(ROUTE_ADD_FARMER) { AddFarmerScreen(navController) }
        composable(ROUTE_VIEW_FARMER) { ViewFarmers(navController) }
        composable("$ROUTE_UPDATE_FARMER/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            UpdateFarmerScreen(navController, id)
        }

        // Product Screens
        composable(ROUTE_ADD_PRODUCE) { AddProductScreen(navController) }
        composable(ROUTE_VIEW_PRODUCE) { ViewProduce(navController) }
        composable("$ROUTE_EDIT_PRODUCE/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            UpdateProductScreen(navController, id)
        }

        // Payment Screen
        composable(ROUTE_PAY) {
            PaymentScreen(navController)
        }
    }
}
