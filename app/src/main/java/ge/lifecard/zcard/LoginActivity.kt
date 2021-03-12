package ge.lifecard.zcard

import android.content.Intent
import android.net.nsd.NsdManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import ge.lifecard.zcard.app.DataStore
import ge.lifecard.zcard.databinding.ActivityLoginBinding
import ge.lifecard.zcard.network.NetworkClient
import ge.lifecard.zcard.utils.LanguageAwareActivity
import ge.lifecard.zcard.utils.LoadingDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class LoginActivity : LanguageAwareActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)

        binding.buttonLogoin.setOnClickListener(this)
        binding.buttonRegistration.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.buttonLogoin -> {
                login()
            }
            binding.buttonRegistration -> {
                registration()
            }
        }
    }

    private fun registration() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }


    private fun login() = lifecycleScope.launchWhenCreated {
        val userName = binding.userEditText.text
        val password = binding.passwordEditText.text

        if (userName.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this@LoginActivity, "გთხოვთ შეავსოთ ველები", Toast.LENGTH_SHORT).show()
            return@launchWhenCreated
        }

        try {
            showLoading()
            val response = withContext(Dispatchers.IO) {
                NetworkClient.userService.login(
                    email = userName.toString(),
                    password = password.toString()
                )
            }
            if (response.isSuccessful) {
                DataStore.accessToken = response.body()?.accessToken!!
                DataStore.setUserData(response.body()!!)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
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
        }
    }
}