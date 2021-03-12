package ge.lifecard.zcard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import ge.lifecard.zcard.databinding.ActivityRegisterBinding
import ge.lifecard.zcard.network.NetworkClient
import ge.lifecard.zcard.utils.LanguageAwareActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.String

class RegisterActivity : LanguageAwareActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowBack.setOnClickListener(this)
        binding.buttonRegistration.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.arrowBack -> {
                finish()
            }
            binding.buttonRegistration -> {
                registartion()
            }
        }
    }


    private fun registartion() = lifecycleScope.launchWhenCreated {
        val userName = binding.userEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()

        if (userName.isNullOrEmpty() || password.isNullOrEmpty() || firstName.isNullOrEmpty() || lastName.isNullOrEmpty()) {
            Toast.makeText(this@RegisterActivity, "გთხოვთ შეავსოთ ველები", Toast.LENGTH_SHORT).show()
            return@launchWhenCreated
        }

        try {
            showLoading()
            val response = withContext(Dispatchers.IO) {
                NetworkClient.userService.registration(
                    email = userName,
                    password = password,
                    firstname = firstName,
                    lastname = lastName,
                    avatar = null
                )
            }
            if (response.isSuccessful) {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            } else {
                val messageText = when (response.code()) {
                    403 -> "Error 403"
                    302 -> "User already exists"
                    else -> response.code().toString()
                }
                Toast.makeText(applicationContext, messageText, Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        } finally {
            hideLoading()
        }

    }

}