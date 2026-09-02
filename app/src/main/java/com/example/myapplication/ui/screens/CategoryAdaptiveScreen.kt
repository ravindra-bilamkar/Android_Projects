package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.viewmodel.CartViewModel
import com.example.myapplication.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CategoryAdaptiveScreen(
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    initialCategoryId: String? = null
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<String>()
    val categories by homeViewModel.categories.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(initialCategoryId) {
        if (initialCategoryId != null) {
            scope.launch {
                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, initialCategoryId)
            }
        }
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
                    Column {
                        Text(
                            "Categories",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                        LazyColumn {
                            items(categories) { category ->
                                ListItem(
                                    headlineContent = { Text(category.name) },
                                    modifier = Modifier.clickable {
                                        scope.launch {
                                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, category.id)
                                        }
                                    },
                                    colors = if (navigator.currentDestination?.contentKey == category.id) {
                                        ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                                    } else {
                                        ListItemDefaults.colors()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        detailPane = {
            val categoryId = navigator.currentDestination?.contentKey
            AnimatedPane {
                if (categoryId != null) {
                    val products = homeViewModel.getProductsByCategory(categoryId)
                    val categoryName = categories.find { it.id == categoryId }?.name ?: "Category"
                    CategoryScreen(
                        categoryName = categoryName,
                        products = products,
                        onAddToCart = { cartViewModel.addProduct(it) },
                        onBack = { 
                            if (navigator.canNavigateBack()) {
                                scope.launch {
                                    navigator.navigateBack()
                                }
                            }
                        }
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Select a category to view products")
                    }
                }
            }
        }
    )
}
