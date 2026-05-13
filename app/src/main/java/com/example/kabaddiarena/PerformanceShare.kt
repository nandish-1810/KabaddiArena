package com.example.kabaddiarena

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object PerformanceShare {

    fun createPerformanceBitmap(
        successRate: Int,
        totalRaids: Int,
        successfulRaids: Int,
        touchPoints: Int,
        emptyRaids: Int,
        superTackles: Int,
        bonusPoints: Int,
        tackles: Int
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
            android.graphics.Color.parseColor("#0F172A")
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
            "Performance Summary",
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
        context: Context,
        bitmap: Bitmap
    ) {

        val file = File(
            context.cacheDir,
            "performance.png"
        )

        val stream = FileOutputStream(file)

        bitmap.compress(
            Bitmap.CompressFormat.PNG,
            100,
            stream
        )

        stream.flush()
        stream.close()

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {

            type = "image/png"

            putExtra(
                Intent.EXTRA_STREAM,
                uri
            )

            putExtra(
                Intent.EXTRA_TEXT,
                "Check out my Kabaddi Arena performance!"
            )

            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        val chooser = Intent.createChooser(
            shareIntent,
            "Share Performance Card"
        )

        context.startActivity(chooser)
    }
}