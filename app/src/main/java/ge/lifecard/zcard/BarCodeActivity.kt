package ge.lifecard.zcard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ge.lifecard.zcard.databinding.ActivityBarCodeBinding
import ge.lifecard.zcard.utils.CardHelper

class BarCodeActivity : AppCompatActivity(), View.OnClickListener {
    private var cardHelper = CardHelper()
    private lateinit var binding: ActivityBarCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val cardNumber = intent.getStringExtra("cardNumber")
        val genBarCode = cardNumber?.let { cardHelper.generateBarCode(it) }
        binding.barCodeImage.setImageBitmap(genBarCode)
        binding.cardNumber.text = cardNumber
        binding.arrowBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.arrowBack -> {
                finish()
            }
        }
    }

}