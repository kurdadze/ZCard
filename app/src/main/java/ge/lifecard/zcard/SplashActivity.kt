package ge.lifecard.zcard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import ge.lifecard.zcard.app.DataStore
import ge.lifecard.zcard.databinding.ActivitySplashBinding
import kotlin.concurrent.thread

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.insetsController?.hide(WindowInsets.Type.statusBars())

        thread {
            for (i in 1..100) {
                binding.progressBar.progress = i
                Thread.sleep(8)
            }
            if (DataStore.accessToken != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }
    }
}