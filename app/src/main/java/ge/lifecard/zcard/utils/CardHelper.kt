package ge.lifecard.zcard.utils

import android.graphics.Bitmap
import android.util.Base64
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.ByteArrayOutputStream

class CardHelper {
    var multiFormatWriter = MultiFormatWriter()

    // ბარკოდის გენერაცია
    fun generateBarCode(code: String): Bitmap {
        val bitmap : Bitmap
        val bitMatrix: BitMatrix = multiFormatWriter.encode(code, BarcodeFormat.CODE_128, 400, 170, null)
        val barCodeEncoder = BarcodeEncoder()
        bitmap = barCodeEncoder.createBitmap(bitMatrix)
        return bitmap
    }

    // Bitmap-დან Base64-ს მიღება
    private fun toBase64(bitmap: Bitmap): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}