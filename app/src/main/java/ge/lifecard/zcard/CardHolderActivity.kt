package ge.lifecard.zcard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.zxing.integration.android.IntentIntegrator
import ge.lifecard.zcard.app.DataStore
import ge.lifecard.zcard.databinding.ActivityCardHolderBinding
import ge.lifecard.zcard.network.NetworkClient
import ge.lifecard.zcard.utils.CardHelper
import ge.lifecard.zcard.utils.LanguageAwareActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


class CardHolderActivity : LanguageAwareActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCardHolderBinding
    private var cardHelper = CardHelper()
    var brandId = ""
    var brandLogo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        brandLogo = intent.getStringExtra("brandLogo")
        brandId = intent.getStringExtra("brandId")
        Glide.with(this)
            .load(brandLogo)
            .into(binding.brandLogo)

        binding.cardNumberEditText.setDrawableClickListener {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }
        binding.arrowBack.setOnClickListener(this)
        binding.acceptLoyaltyCardButton.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, getString(R.string.canceled), Toast.LENGTH_LONG).show()
            } else {
                binding.cardNumberEditText.setText(result.contents)
                val generitedCard = cardHelper.generateBarCode(binding.cardNumberEditText.text.toString())
                binding.brandLogo.setImageBitmap(generitedCard)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.arrowBack -> {
                finish()
            }
            binding.acceptLoyaltyCardButton -> {
                acceptLoyaltyCard()
            }
        }
    }

    private fun acceptLoyaltyCard() = lifecycleScope.launchWhenCreated {
        val cardNumber = binding.cardNumberEditText.text.toString()
        val cardHolder = binding.cardHolderEditText.text.toString()
        val moreInfo = binding.moreInfoEditText.text.toString()

        if (cardNumber.isNullOrEmpty() || cardHolder.isNullOrEmpty()) {
            Toast.makeText(this@CardHolderActivity, "გთხოვთ შეავსოთ ველები", Toast.LENGTH_SHORT).show()
            return@launchWhenCreated
        }

        try {
            showLoading()
            val response = withContext(Dispatchers.IO) {
                NetworkClient.userService.acceptUserCard(
                    userId = DataStore.getUserData().id,
                    cardNumber = cardNumber,
                    cardHolder = cardHolder,
                    moreInfo = moreInfo,
                    brandId = brandId
                )
            }
            if (response.isSuccessful) {
//
            } else {
                val messageText = when (response.code()) {
                    403 -> getString(R.string.user_password_incorrect)
                    else -> response.code().toString()
                }
                Toast.makeText(applicationContext, messageText, Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        } finally {
            hideLoading()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}