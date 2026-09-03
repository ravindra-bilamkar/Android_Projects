package com.example.myapplication.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.data.model.Product
import com.example.myapplication.ui.viewmodel.CartViewModel
import java.util.Locale

@Composable
fun CartScreen(viewModel: CartViewModel, onCheckout: (Double) -> Unit) {
    val cartItems by viewModel.cartState.collectAsState()
    val slotToday = stringResource(R.string.slot_today)
    var selectedSlot by remember { mutableStateOf(slotToday) }
    var tipAmount by remember { mutableIntStateOf(0) }
    var useLoyaltyPoints by remember { mutableStateOf(false) }
    var couponCode by remember { mutableStateOf("") }
    var isBusinessInvoice by remember { mutableStateOf(false) }
    var gstin by remember { mutableStateOf("") }
    
    val itemTotal = cartItems.entries.sumOf { it.key.price * it.value }
    val handlingFee = 5.0
    val deliveryFee = if (itemTotal >= 500) 0.0 else 25.0
    val gst = itemTotal * 0.05 // 5% GST
    val loyaltyDiscount = if (useLoyaltyPoints) 50.0 else 0.0
    
    val grandTotal = (itemTotal + handlingFee + deliveryFee + gst + tipAmount - loyaltyDiscount).coerceAtLeast(0.0)

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Text(
            text = stringResource(R.string.your_cart),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(16.dp)
        )

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.cart_empty), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                items(cartItems.toList()) { (product, quantity) ->
                    CartItemRow(product, quantity, viewModel)
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    DeliverySlotPicker(selectedSlot) { selectedSlot = it }
                    Spacer(modifier = Modifier.height(24.dp))
                    CouponSection(couponCode) { couponCode = it }
                    Spacer(modifier = Modifier.height(24.dp))
                    LoyaltyRedeemSection(useLoyaltyPoints) { useLoyaltyPoints = it }
                    Spacer(modifier = Modifier.height(24.dp))
                    BusinessInvoiceSection(isBusinessInvoice, gstin, { isBusinessInvoice = it }, { gstin = it })
                    Spacer(modifier = Modifier.height(24.dp))
                    TipSection(tipAmount) { tipAmount = it }
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    BillSummary(
                        itemTotal = itemTotal,
                        handlingFee = handlingFee,
                        deliveryFee = deliveryFee,
                        gst = gst,
                        tip = tipAmount.toDouble(),
                        discount = loyaltyDiscount
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(stringResource(R.string.grand_total), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
                            Text(stringResource(R.string.incl_taxes), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("₹${String.format(Locale.getDefault(), "%.2f", grandTotal)}", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onCheckout(grandTotal) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(stringResource(R.string.proceed_to_pay), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun BillSummary(
    itemTotal: Double,
    handlingFee: Double,
    deliveryFee: Double,
    gst: Double,
    tip: Double,
    discount: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(R.string.bill_summary), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 12.dp))
            
            SummaryRow(stringResource(R.string.item_total), itemTotal)
            SummaryRow(stringResource(R.string.handling_fee), handlingFee)
            SummaryRow(stringResource(R.string.delivery_fee), deliveryFee, isFree = deliveryFee == 0.0)
            SummaryRow(stringResource(R.string.gst_label), gst)
            if (tip > 0) SummaryRow(stringResource(R.string.delivery_partner_tip), tip)
            if (discount > 0) SummaryRow(stringResource(R.string.loyalty_discount), -discount, isDiscount = true)
        }
    }
}

@Composable
fun SummaryRow(label: String, amount: Double, isFree: Boolean = false, isDiscount: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        if (isFree) {
            Text(stringResource(R.string.free), style = MaterialTheme.typography.bodySmall, color = Color(0xFF00B259), fontWeight = FontWeight.Bold)
        } else {
            Text(
                text = "₹${String.format(Locale.getDefault(), "%.2f", amount)}",
                style = MaterialTheme.typography.bodySmall,
                color = if (isDiscount) Color(0xFF00B259) else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isDiscount) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun CartItemRow(product: Product, quantity: Int, viewModel: CartViewModel) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(50.dp).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(product.name.take(1), color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(text = "₹${product.price}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = stringResource(R.string.save_for_later),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.clickable { /* Save for later */ }.padding(top = 4.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
            ) {
                IconButton(onClick = { viewModel.removeProduct(product) }, modifier = Modifier.size(30.dp)) {
                    Text("-", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                Text(text = quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                IconButton(onClick = { viewModel.addProduct(product) }, modifier = Modifier.size(30.dp)) {
                    Text("+", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun CouponSection(code: String, onCodeChange: (String) -> Unit) {
    Column {
        Text(stringResource(R.string.coupons), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = code,
            onValueChange = onCodeChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.coupon_placeholder), style = MaterialTheme.typography.bodySmall) },
            trailingIcon = {
                TextButton(onClick = { /* Apply */ }) {
                    Text(stringResource(R.string.apply), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}

@Composable
fun LoyaltyRedeemSection(usePoints: Boolean, onToggle: (Boolean) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFFFF8E1),
        border = BorderStroke(1.dp, Color(0xFFFFD54F))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.redeem_loyalty_points, 500), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF5D4037))
                Text(stringResource(R.string.save_amount_on_order, 50), style = MaterialTheme.typography.bodySmall, color = Color(0xFF795548))
            }
            Switch(
                checked = usePoints, 
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFFFA000))
            )
        }
    }
}

@Composable
fun BusinessInvoiceSection(
    isBusiness: Boolean,
    gstin: String,
    onToggle: (Boolean) -> Unit,
    onGstinChange: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.business_invoice), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Checkbox(checked = isBusiness, onCheckedChange = onToggle)
        }
        if (isBusiness) {
            OutlinedTextField(
                value = gstin,
                onValueChange = onGstinChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.enter_gstin), style = MaterialTheme.typography.bodySmall) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(stringResource(R.string.gstin_invoice_note), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
fun DeliverySlotPicker(selectedSlot: String, onSlotSelected: (String) -> Unit) {
    val slots = listOf(
        stringResource(R.string.slot_today),
        stringResource(R.string.slot_tomorrow_morning),
        stringResource(R.string.slot_tomorrow_evening)
    )
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.delivery_slot), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            slots.forEach { slot ->
                FilterChip(
                    selected = selectedSlot == slot,
                    onClick = { onSlotSelected(slot) },
                    label = { Text(slot, style = MaterialTheme.typography.labelSmall) },
                    shape = RoundedCornerShape(8.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
fun TipSection(currentTip: Int, onTipSelected: (Int) -> Unit) {
    val tips = listOf(10, 20, 50)
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.add_tip_partner), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
        Text(stringResource(R.string.tip_note), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            tips.forEach { amount ->
                OutlinedCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTipSelected(if (currentTip == amount) 0 else amount) },
                    border = BorderStroke(1.dp, if (currentTip == amount) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = if (currentTip == amount) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.Center) {
                        Text("₹$amount", fontWeight = FontWeight.Bold, color = if (currentTip == amount) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}
