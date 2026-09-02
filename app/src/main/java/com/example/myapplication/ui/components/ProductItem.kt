package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.model.StockStatus

@Composable
fun ProductItem(
    product: Product,
    onAddToCart: (Product) -> Unit,
    onProductClick: (Product) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onProductClick(product) }
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Product Image Area
        val bowlShape = RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50, topStartPercent = 15, topEndPercent = 15)
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(bowlShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f), bowlShape),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.05f))
            ) {
                if (product.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = product.name.take(1),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                        )
                    }
                }
            }
            
            // Rating Badge
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp, end = 8.dp),
                shape = RoundedCornerShape(4.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.rating.toString(), 
                        style = MaterialTheme.typography.labelSmall, 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(10.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Product Name
        Text(
            text = product.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // Stock Status
        if (product.stockStatus == StockStatus.LowStock) {
            Text(
                text = "Only few left!",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        } else if (product.stockStatus == StockStatus.OutOfStock) {
            Text(
                text = "Out of Stock",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        } else {
            Spacer(modifier = Modifier.height(14.dp))
        }
        
        // Price
        Text(
            text = "₹${product.price}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Add Button
        Button(
            onClick = { onAddToCart(product) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(36.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = product.stockStatus != StockStatus.OutOfStock,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = if (product.stockStatus == StockStatus.OutOfStock) "NOTIFY ME" else "ADD",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
