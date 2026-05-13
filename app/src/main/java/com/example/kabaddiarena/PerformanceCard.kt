package com.example.kabaddiarena

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

@Composable
fun PerformanceCard(
    touchPoints: Int,
    emptyRaids: Int,
    superTackles: Int,
    bonusPoints: Int,
    tackles: Int,
    successRate: Int,
    totalRaids: Int,
    successfulRaids: Int
) {

    val context = LocalContext.current

    val performanceColor = when {

        successRate >= 80 ->
            Color(0xFF00E676)

        successRate >= 50 ->
            Color(0xFFFFB300)

        else ->
            Color(0xFFFF5252)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                Color.White.copy(alpha = 0.10f)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Performance Card",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            LinearProgressIndicator(
                progress = { successRate / 100f },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),

                color = performanceColor,

                trackColor = Color.DarkGray
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                StatItem(
                    title = "Success",
                    value = "$successRate%",
                    color = performanceColor
                )

                StatItem(
                    title = "Raids",
                    value = totalRaids.toString(),
                    color = Color.White
                )

                StatItem(
                    title = "Won",
                    value = successfulRaids.toString(),
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            DetailRow("Touch Points", touchPoints)
            DetailRow("Empty Raids", emptyRaids)
            DetailRow("Super Tackles", superTackles)
            DetailRow("Bonus Points", bonusPoints)
            DetailRow("Tackles", tackles)

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {

                    val bitmap =
                        createPerformanceBitmap(
                            touchPoints,
                            emptyRaids,
                            superTackles,
                            bonusPoints,
                            tackles,
                            successRate,
                            totalRaids,
                            successfulRaids
                        )

                    sharePerformanceCard(
                        context,
                        bitmap
                    )
                },

                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFFFF7A00)
                )
            ) {

                Text(
                    text = "Share Performance Card",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun StatItem(
    title: String,
    value: String,
    color: Color
) {

    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = value,
            color = color,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = title,
            color = Color.LightGray
        )
    }
}

@Composable
fun DetailRow(
    title: String,
    value: Int
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),

        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            color = Color.White
        )

        Text(
            text = value.toString(),
            color = Color(0xFFFFB300)
        )
    }
}

fun createPerformanceBitmap(
    touchPoints: Int,
    emptyRaids: Int,
    superTackles: Int,
    bonusPoints: Int,
    tackles: Int,
    successRate: Int,
    totalRaids: Int,
    successfulRaids: Int
): Bitmap {

    val width = 1080
    val height = 1200

    val bitmap = Bitmap.createBitmap(
        width,
        height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)

    canvas.drawColor(
        Color(0xFF0F172A).toArgb()
    )

    val paint = Paint().apply {

        color = android.graphics.Color.WHITE
        textSize = 60f
        isFakeBoldText = true
    }

    canvas.drawText(
        "Kabaddi Arena",
        80f,
        120f,
        paint
    )

    paint.textSize = 45f

    canvas.drawText(
        "Performance Overview",
        80f,
        220f,
        paint
    )

    paint.textSize = 40f

    canvas.drawText(
        "Success Rate : $successRate%",
        80f,
        340f,
        paint
    )

    canvas.drawText(
        "Total Raids : $totalRaids",
        80f,
        430f,
        paint
    )

    canvas.drawText(
        "Successful Raids : $successfulRaids",
        80f,
        520f,
        paint
    )

    canvas.drawText(
        "Touch Points : $touchPoints",
        80f,
        610f,
        paint
    )

    canvas.drawText(
        "Empty Raids : $emptyRaids",
        80f,
        700f,
        paint
    )

    canvas.drawText(
        "Super Tackles : $superTackles",
        80f,
        790f,
        paint
    )

    canvas.drawText(
        "Bonus Points : $bonusPoints",
        80f,
        880f,
        paint
    )

    canvas.drawText(
        "Tackles : $tackles",
        80f,
        970f,
        paint
    )

    return bitmap
}

fun sharePerformanceCard(
    context: android.content.Context,
    bitmap: Bitmap
) {

    val file = File(
        context.cacheDir,
        "performance.png"
    )

    val stream =
        FileOutputStream(file)

    bitmap.compress(
        Bitmap.CompressFormat.PNG,
        100,
        stream
    )

    stream.flush()
    stream.close()

    val uri: Uri =
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

    val shareIntent = Intent().apply {

        action = Intent.ACTION_SEND

        putExtra(
            Intent.EXTRA_STREAM,
            uri
        )

        type = "image/png"

        addFlags(
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }

    context.startActivity(
        Intent.createChooser(
            shareIntent,
            "Share Performance Card"
        )
    )
}