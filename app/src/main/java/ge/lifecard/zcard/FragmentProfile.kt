package ge.lifecard.zcard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import ge.lifecard.zcard.app.DataStore
import ge.lifecard.zcard.databinding.FragmentProfileBinding
import ge.lifecard.zcard.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


class FragmentProfile : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
//    private val context = getActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.geoFlag.setOnClickListener(this)
        binding.engFlag.setOnClickListener(this)
        binding.buttonLogout.setOnClickListener(this)

        binding.userFirstLastNameLabelText.text =
            DataStore.getUserData().firstName.plus(" ").plus(DataStore.getUserData().lastName)
        binding.userEmailLabelText.text = DataStore.getUserData().email

        if (!DataStore.getUserData().avatarPath.isNullOrEmpty())
            Glide.with(this@FragmentProfile)
                .load(DataStore.getUserData().avatarPath)
                .centerCrop()
                .into(binding.profileImage)
        return binding.root
    }

    private fun logout() = lifecycleScope.launchWhenCreated {
        val userData = DataStore.getUserData()
        val userId = userData.id
        try {
            DataStore.clearUserData()
            val response = withContext(Dispatchers.IO) { NetworkClient.userService.logout(userId) }
            if (response.isSuccessful) {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        } finally {
//            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.geoFlag -> {
                DataStore.language = "ka"
                activity?.recreate()
            }
            binding.engFlag -> {
                DataStore.language = "en"
                activity?.recreate()
            }
            binding.buttonLogout -> {
                logout()
            }
        }
    }
}
