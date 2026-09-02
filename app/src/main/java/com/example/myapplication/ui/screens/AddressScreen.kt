package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(onBack: () -> Unit) {
    var showMapMock by remember { mutableStateOf(false) }
    val addresses = listOf("Home: 123, Green Valley, Bangalore", "Work: Tech Park, Electronic City")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Addresses") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showMapMock = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add New Address") }
            )
        }
    ) { innerPadding ->
        if (showMapMock) {
            MapMock(onClose = { showMapMock = false })
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(addresses) { address ->
                    AddressItem(address)
                }
            }
        }
    }
}

@Composable
fun AddressItem(address: String) {
    val (label, detail) = address.split(": ")
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (label == "Home") Icons.Default.Home else Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(label, fontWeight = FontWeight.Bold)
                Text(detail, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun MapMock(onClose: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE0E0E0)),
        contentAlignment = Alignment.Center
    ) {
        // Simple Grid to represent a map
        Column {
            repeat(10) {
                Row {
                    repeat(10) {
                        Box(modifier = Modifier.size(40.dp).border(0.5.dp, Color.LightGray))
                    }
                }
            }
        }
        
        // Crosshair
        Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Red)
        
        Card(
            modifier = Modifier.align(Alignment.BottomCenter).padding(24.dp).fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Pin your location", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onClose, modifier = Modifier.fillMaxWidth()) {
                    Text("Confirm Location")
                }
            }
        }
    }
}
