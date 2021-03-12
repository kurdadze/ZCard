package ge.lifecard.zcard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import ge.lifecard.zcard.app.DataStore
import ge.lifecard.zcard.databinding.ActivitySplashBinding
import kotlin.concurrent.thread

class SplashActivity : Activity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

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