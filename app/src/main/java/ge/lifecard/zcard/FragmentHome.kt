package ge.lifecard.zcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ge.lifecard.zcard.adapters.UserCardsAdapter
import ge.lifecard.zcard.app.DataStore
import ge.lifecard.zcard.databinding.FragmentHomeBinding
import ge.lifecard.zcard.models.BrandsCard
import ge.lifecard.zcard.models.UserCards
import ge.lifecard.zcard.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FragmentHome : Fragment() {

//    private lateinit var mContext: Context
    private var mContext: Context? = null

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            try {
                val response = withContext(Dispatchers.IO) {
                    NetworkClient.userService.getUserCards(
                        userId = DataStore.getUserData().id
                    )
                }
                Log.d("responce", response.toString())
                binding.userCardsListView.adapter = mContext?.let { UserCardsAdapter(it, response) }
            } catch (e: Exception) {
//                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show()
            } finally {
//
            }
        }

        binding.userCardsListView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val item = parent.adapter.getItem(position) as UserCards
                val intent = Intent(context, BarCodeActivity::class.java)
                intent.putExtra("cardNumber", item.cardNumber)
                startActivity(intent)
            }
        }

        binding.fab.setOnClickListener {barndsCardList()}
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    private fun barndsCardList() {
        val intent = Intent(this.activity, BrandsCardsActivity::class.java)
        startActivity(intent)
    }
}