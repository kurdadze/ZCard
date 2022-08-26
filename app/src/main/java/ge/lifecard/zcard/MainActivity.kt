package ge.lifecard.zcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import ge.lifecard.zcard.app.DataStore
import ge.lifecard.zcard.databinding.ActivityMainBinding
import ge.lifecard.zcard.utils.LanguageAwareActivity

class MainActivity : LanguageAwareActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentHome = FragmentHome()
        val fragmentProfile = FragmentProfile()
        val fragmentOptions = FragmentOptions()

        setCurrentFragment(fragmentHome)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> setCurrentFragment(fragmentHome)
                R.id.nav_profile -> setCurrentFragment(fragmentProfile)
                R.id.nav_options -> setCurrentFragment(fragmentOptions)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    override fun onResume() {
        super.onResume()
        if (DataStore.accessToken == null) {
            finish()
        }
    }

}