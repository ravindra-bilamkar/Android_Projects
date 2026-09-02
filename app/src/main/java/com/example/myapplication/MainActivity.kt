package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.CartViewModel
import com.example.myapplication.ui.viewmodel.HomeViewModel
import com.example.myapplication.ui.viewmodel.LoginViewModel
import com.example.myapplication.ui.viewmodel.PaymentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                val homeViewModel: HomeViewModel = viewModel()
                val cartViewModel: CartViewModel = viewModel()
                val loginViewModel: LoginViewModel = viewModel()
                val paymentViewModel: PaymentViewModel = viewModel()
                
                var showSplash by remember { mutableStateOf(true) }
                var isAuthenticated by remember { mutableStateOf(false) }
                var isGuestMode by remember { mutableStateOf(false) }
                var currentRoute by remember { mutableStateOf("home") }

                var locationPermissionGranted by remember { 
                    mutableStateOf(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED)
                }

                val permissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    locationPermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
                    if (!locationPermissionGranted) {
                        Toast.makeText(context, "Location permission is needed for delivery detection", Toast.LENGTH_SHORT).show()
                    }
                }

                LaunchedEffect(isAuthenticated, isGuestMode) {
                    if (isAuthenticated || isGuestMode) {
                        val permissions = mutableListOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        permissionLauncher.launch(permissions.toTypedArray())
                    }
                }

                val loggedPhone by loginViewModel.mobileNumber.collectAsState()
                val isAdmin = loggedPhone == "9008882827"

                // Error Handling
                val loginError by loginViewModel.error.collectAsState()
                LaunchedEffect(loginError) {
                    loginError?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }

                if (showSplash) {
                    SplashScreen(onTimeout = { showSplash = false })
                } else if (!isAuthenticated && !isGuestMode) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                viewModel = loginViewModel,
                                onAuthenticated = { isAuthenticated = true },
                                onGuestMode = { isGuestMode = true },
                                onSignupClick = { navController.navigate("signup") }
                            )
                        }
                        composable("signup") {
                            SignupScreen(
                                onBack = { navController.popBackStack() },
                                onSignupComplete = { name, email, mobile ->
                                    // Simulated Signup logic
                                    isAuthenticated = true
                                    Toast.makeText(context, "Welcome $name!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                } else {
                    NavigationSuiteScaffold(
                        navigationSuiteItems = {
                            item(
                                selected = currentRoute == "home",
                                onClick = {
                                    currentRoute = "home"
                                    navController.navigate("home") {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                label = { Text("Home") }
                            )
                            item(
                                selected = currentRoute == "orders",
                                onClick = {
                                    currentRoute = "orders"
                                    navController.navigate("tracking")
                                },
                                icon = { Icon(Icons.Default.List, contentDescription = "Orders") },
                                label = { Text("Orders") }
                            )
                            item(
                                selected = currentRoute == "cart",
                                onClick = {
                                    currentRoute = "cart"
                                    navController.navigate("cart") {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                },
                                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                                label = { Text("Cart") }
                            )
                            item(
                                selected = currentRoute == "profile",
                                onClick = {
                                    currentRoute = "profile"
                                    navController.navigate("profile")
                                },
                                icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                                label = { Text("Profile") }
                            )
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "home",
                            modifier = Modifier.fillMaxSize()
                        ) {
                            composable("home") {
                                HomeScreen(
                                    viewModel = homeViewModel,
                                    cartViewModel = cartViewModel,
                                    isAdmin = isAdmin,
                                    locationPermissionGranted = locationPermissionGranted,
                                    onAddToCart = { 
                                        cartViewModel.addProduct(it)
                                        Toast.makeText(context, "${it.name} added to cart", Toast.LENGTH_SHORT).show()
                                    },
                                    onProductClick = { product ->
                                        navController.navigate("product/${product.id}")
                                    },
                                    onCategoryClick = { category ->
                                        if (category.id == "custom") {
                                            navController.navigate("custom_mix")
                                        } else {
                                            navController.navigate("category/${category.id}")
                                        }
                                    },
                                    onGiftHamperClick = {
                                        navController.navigate("gift_hamper")
                                    },
                                    onViewCart = {
                                        navController.navigate("cart")
                                        currentRoute = "cart"
                                    },
                                    onAddItemClick = {
                                        navController.navigate("add_item")
                                    },
                                    onUpdateStockClick = {
                                        navController.navigate("update_stock")
                                    },
                                    onNotificationsClick = {
                                        navController.navigate("notifications")
                                    }
                                )
                            }
                            composable("update_stock") {
                                UpdateStockScreen(
                                    viewModel = homeViewModel,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                            composable("profile") {
                                ProfileScreen(
                                    onBack = { navController.popBackStack() },
                                    onLogout = {
                                        isAuthenticated = false
                                        isGuestMode = false
                                        loginViewModel.backToMobile()
                                    },
                                    onManageAddresses = {
                                        navController.navigate("addresses")
                                    },
                                    onSubscriptionsClick = { navController.navigate("subscriptions") },
                                    onNotificationsClick = { navController.navigate("notifications") },
                                    onStoreLocatorClick = { navController.navigate("store_locator") },
                                    onSupportClick = { navController.navigate("support") }
                                )
                            }
                            composable("notifications") {
                                NotificationScreen(onBack = { navController.popBackStack() })
                            }
                            composable("subscriptions") {
                                SubscriptionScreen(onBack = { navController.popBackStack() })
                            }
                            composable("store_locator") {
                                StoreLocatorScreen(onBack = { navController.popBackStack() })
                            }
                            composable("custom_mix") {
                                CustomMixScreen(
                                    onBack = { navController.popBackStack() },
                                    onAddToCart = { name, price ->
                                        cartViewModel.addProduct(com.example.myapplication.data.model.Product(
                                            id = "custom_${System.currentTimeMillis()}",
                                            name = name,
                                            price = price,
                                            category = "Custom Mix",
                                            imageUrl = ""
                                        ))
                                        Toast.makeText(context, "$name added to cart", Toast.LENGTH_SHORT).show()
                                        navController.navigate("cart")
                                        currentRoute = "cart"
                                    }
                                )
                            }
                            composable("addresses") {
                                AddressScreen(onBack = { navController.popBackStack() })
                            }
                            composable("add_item") {
                                AddItemScreen(
                                    viewModel = homeViewModel,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                            composable(
                                route = "category/{categoryId}",
                                arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val categoryId = backStackEntry.arguments?.getString("categoryId")
                                CategoryAdaptiveScreen(
                                    homeViewModel = homeViewModel,
                                    cartViewModel = cartViewModel,
                                    initialCategoryId = categoryId
                                )
                            }
                            composable(
                                route = "product/{productId}",
                                arguments = listOf(navArgument("productId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                                val product = homeViewModel.getProductById(productId)
                                val recommendations = homeViewModel.featuredProducts.collectAsState().value.filter { it.id != productId }
                                if (product != null) {
                                    ProductDetailScreen(
                                        product = product,
                                        recommendations = recommendations,
                                        onBack = { navController.popBackStack() },
                                        onAddToCart = { 
                                            cartViewModel.addProduct(it)
                                            Toast.makeText(context, "${it.name} added to cart", Toast.LENGTH_SHORT).show()
                                        },
                                        onBuyNow = { 
                                            cartViewModel.addProduct(it)
                                            navController.navigate("cart")
                                            currentRoute = "cart"
                                        },
                                        onProductClick = { newProduct ->
                                            navController.navigate("product/${newProduct.id}")
                                        }
                                    )
                                }
                            }
                            composable("gift_hamper") {
                                GiftHamperBuilder(
                                    onBack = { navController.popBackStack() },
                                    onHamperComplete = { items, _ ->
                                        items.forEach { cartViewModel.addProduct(it) }
                                        Toast.makeText(context, "Gift Hamper added to cart", Toast.LENGTH_SHORT).show()
                                        navController.navigate("cart")
                                        currentRoute = "cart"
                                    }
                                )
                            }
                            composable("tracking") {
                                OrderTrackingScreen(onBack = { navController.popBackStack() })
                            }
                            composable("support") {
                                SupportCenter(onBack = { navController.popBackStack() })
                            }
                            composable("cart") {
                                CartScreen(
                                    viewModel = cartViewModel,
                                    onCheckout = { total ->
                                        if (isGuestMode) {
                                            Toast.makeText(context, "Please login to checkout", Toast.LENGTH_SHORT).show()
                                            isGuestMode = false // Trigger login
                                        } else {
                                            navController.navigate("payment/$total")
                                        }
                                    }
                                )
                            }
                            composable(
                                route = "payment/{amount}",
                                arguments = listOf(navArgument("amount") { type = NavType.FloatType })
                            ) { backStackEntry ->
                                val amount = backStackEntry.arguments?.getFloat("amount")?.toDouble() ?: 0.0
                                PaymentScreen(
                                    amount = amount,
                                    viewModel = paymentViewModel,
                                    onPaymentSuccess = {
                                        cartViewModel.clearCart()
                                        navController.navigate("tracking")
                                        currentRoute = "orders"
                                    },
                                    onBackToCart = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
