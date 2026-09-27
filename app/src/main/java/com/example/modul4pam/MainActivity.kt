package com.example.modul4pam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ==========================================
// 🎨 PALET WARNA PINK AESTHETIC & MODERN
// ==========================================
val PinkBgStart = Color(0xFFFFF0F5)
val PinkBgEnd = Color(0xFFFCE4EC)
val PrimaryPink = Color(0xFFEC407A)
val DarkPink = Color(0xFFAD1457)
val DeepMagenta = Color(0xFF880E4F)
val SoftPinkCard = Color(0xFFFFF5F8)
val AccentPinkBorder = Color(0xFFF8BBD0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PinkBgStart
                ) {
                    TicketBookingParent()
                }
            }
        }
    }
}

// Format Rupiah Sederhana
fun formatRupiah(amount: Int): String {
    return "Rp " + String.format("%,d", amount).replace(',', '.')
}

@Composable
fun TicketBookingParent() {

    var ticketPrice by rememberSaveable { mutableStateOf(150000) } // 1. Harga Tiket
    var ticketCount by rememberSaveable { mutableStateOf(1) }      // 2. Jumlah Tiket
    var buyerName by rememberSaveable { mutableStateOf("") }        // 3. Nama Pembeli Tiket

    var statusMessage by rememberSaveable { mutableStateOf("Status: Silakan isi nama & pesan tiket") }
    var statusType by rememberSaveable { mutableStateOf("IDLE") } // "IDLE", "EMPTY", "PROCESSING", "SUCCESS"
    var isProcessing by rememberSaveable { mutableStateOf(false) }
    var orderTrigger by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(orderTrigger) {
        if (orderTrigger > 0) {
            if (buyerName.trim().isEmpty()) {
                statusMessage = "Status : Nama Masih Kosong"
                statusType = "EMPTY"
                isProcessing = false
            } else {

                isProcessing = true
                statusType = "PROCESSING"
                statusMessage = "Status : Memproses pesanan........."

                delay(5000) // Penundaan 5 detik

                statusMessage = "Status : Tiket telah dipesan"
                statusType = "SUCCESS"
                isProcessing = false
            }
        }
    }


    TicketBookingContent(
        buyerName = buyerName,
        onBuyerNameChange = { buyerName = it },
        ticketCount = ticketCount,
        onTicketCountChange = { if (it >= 1) ticketCount = it },
        ticketPrice = ticketPrice,
        statusMessage = statusMessage,
        statusType = statusType,
        isProcessing = isProcessing,
        onOrderClick = { orderTrigger++ }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBookingContent(
    buyerName: String,
    onBuyerNameChange: (String) -> Unit,
    ticketCount: Int,
    onTicketCountChange: (Int) -> Unit,
    ticketPrice: Int,
    statusMessage: String,
    statusType: String,
    isProcessing: Boolean,
    onOrderClick: () -> Unit
) {
    val totalPrice = formatRupiah(ticketPrice * ticketCount)
    val formattedPrice = formatRupiah(ticketPrice)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PinkBgStart, PinkBgEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .shadow(8.dp, CircleShape)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(PrimaryPink, DarkPink)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🎟️", fontSize = 28.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Concert Events",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepMagenta
                )
                Text(
                    text = "Pemesanan Tiket Konser Eksklusif",
                    fontSize = 13.sp,
                    color = DarkPink.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(10.dp, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(SoftPinkCard)
                                .border(1.dp, AccentPinkBorder, RoundedCornerShape(14.dp))
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Harga per Tiket",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DeepMagenta
                            )
                            Text(
                                text = formattedPrice,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkPink
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Nama Pembeli Tiket",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepMagenta
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = buyerName,
                            onValueChange = onBuyerNameChange,
                            placeholder = { Text("Masukkan nama lengkap kamu", color = Color.Gray, fontSize = 13.sp) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            enabled = !isProcessing,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryPink,
                                unfocusedBorderColor = AccentPinkBorder,
                                focusedContainerColor = SoftPinkCard,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Jumlah Tiket",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepMagenta
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(SoftPinkCard)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = { onTicketCountChange(ticketCount - 1) },
                                enabled = ticketCount > 1 && !isProcessing,
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(if (ticketCount > 1) PrimaryPink else Color.LightGray)
                            ) {
                                Text(text = "−", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }

                            Text(
                                text = "$ticketCount Tiket",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepMagenta
                            )

                            IconButton(
                                onClick = { onTicketCountChange(ticketCount + 1) },
                                enabled = !isProcessing,
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryPink)
                            ) {
                                Text(text = "+", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AccentPinkBorder.copy(alpha = 0.6f))
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Pembayaran:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.DarkGray
                            )
                            Text(
                                text = totalPrice,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = DarkPink
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onOrderClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .shadow(6.dp, RoundedCornerShape(14.dp)),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryPink,
                                disabledContainerColor = Color.LightGray
                            ),
                            enabled = !isProcessing
                        ) {
                            if (isProcessing) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(22.dp),
                                    color = Color.White,
                                    strokeWidth = 2.5.dp
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Memproses...",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = "Pesan Tiket Sekarang ✨",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            val (cardBg, textColor, statusIcon) = when (statusType) {
                "EMPTY" -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), "⚠️")
                "PROCESSING" -> Triple(Color(0xFFF3E5F5), Color(0xFF7B1FA2), "⏳")
                "SUCCESS" -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), "🎉")
                else -> Triple(SoftPinkCard, DeepMagenta, "💖")
            }

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .border(1.dp, textColor.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = statusIcon, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = statusMessage,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = textColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}