package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.res.stringResource
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onManageAddresses: () -> Unit,
    onSubscriptionsClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onStoreLocatorClick: () -> Unit = {},
    onSupportClick: () -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("User Name") }
    var email by remember { mutableStateOf("user@example.com") }
    var dob by remember { mutableStateOf("01/01/1990") }
    var selectedLanguage by remember { mutableStateOf("English") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_profile)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    TextButton(onClick = { isEditing = !isEditing }) {
                        Text(if (isEditing) stringResource(R.string.save) else stringResource(R.string.edit), fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LoyaltyCard(points = 500)

            if (isEditing) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.full_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.email_address)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = dob,
                    onValueChange = { dob = it },
                    label = { Text(stringResource(R.string.date_of_birth)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            } else {
                ProfileItem(icon = Icons.Default.Person, label = stringResource(R.string.full_name), value = name)
                ProfileItem(icon = Icons.Default.Email, label = stringResource(R.string.email_address), value = email)
                ProfileItem(icon = Icons.Default.DateRange, label = stringResource(R.string.date_of_birth), value = dob)
            }

            ProfileItem(
                icon = Icons.Default.Home, 
                label = stringResource(R.string.saved_addresses), 
                value = stringResource(R.string.manage_addresses),
                onClick = onManageAddresses
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(R.string.activity), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            ProfileItem(
                icon = Icons.Default.Refresh,
                label = stringResource(R.string.subscribe_save),
                value = stringResource(R.string.manage_recurring_orders),
                onClick = onSubscriptionsClick
            )
            ProfileItem(
                icon = Icons.Default.Notifications,
                label = stringResource(R.string.notifications),
                value = stringResource(R.string.view_past_offers),
                onClick = onNotificationsClick
            )
            ProfileItem(
                icon = Icons.Default.LocationOn,
                label = stringResource(R.string.store_locator),
                value = stringResource(R.string.find_outlets_near_you),
                onClick = onStoreLocatorClick
            )
            ProfileItem(
                icon = Icons.Default.Warning,
                label = stringResource(R.string.help_support),
                value = stringResource(R.string.raise_ticket_chat),
                onClick = onSupportClick
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(R.string.settings), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            
            LanguageToggle(selectedLanguage) { selectedLanguage = it }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.logout))
            }
        }
    }
}

@Composable
fun LoyaltyCard(points: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFD700), Color(0xFFFFA500))
                    )
                )
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.loyalty_points), color = Color.White, fontSize = 14.sp)
                    Text("$points Points", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                }
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
            }
        }
    }
}

@Composable
fun LanguageToggle(currentLanguage: String, onLanguageChange: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.language))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    stringResource(R.string.english) to "English",
                    stringResource(R.string.hindi) to "Hindi",
                    stringResource(R.string.kannada) to "Kannada"
                ).forEach { (label, value) ->
                    FilterChip(
                        selected = currentLanguage == value,
                        onClick = { onLanguageChange(value) },
                        label = { Text(label) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileItem(icon: ImageVector, label: String, value: String, onClick: (() -> Unit)? = null) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Column {
                Text(text = label, style = MaterialTheme.typography.labelSmall)
                Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
        }
    }
}
