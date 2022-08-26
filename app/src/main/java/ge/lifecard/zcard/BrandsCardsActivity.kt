package ge.lifecard.zcard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.lifecycle.lifecycleScope
import ge.lifecard.zcard.adapters.BrandsCardAdapter
import ge.lifecard.zcard.databinding.ActivityBrandsCardsBinding
import ge.lifecard.zcard.models.BrandsCard
import ge.lifecard.zcard.network.NetworkClient
import ge.lifecard.zcard.utils.LanguageAwareActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BrandsCardsActivity : LanguageAwareActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBrandsCardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrandsCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowBack.setOnClickListener(this)

        lifecycleScope.launchWhenCreated {
            try {
                showLoading()
                val response = withContext(Dispatchers.IO) {
                    NetworkClient.userService.getBrands()
                }
                Log.d("response", response.toString())
                val brandsCardAdapter = BrandsCardAdapter(applicationContext, response)
                binding.cardGridViewList.adapter = brandsCardAdapter
            } catch (e: Exception) {
                makeText(applicationContext, "Error", LENGTH_SHORT).show()
            } finally {
                hideLoading()
            }
        }

        binding.cardGridViewList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.adapter.getItem(position) as BrandsCard
                val intent = Intent(this@BrandsCardsActivity, CardHolderActivity::class.java)
                val extras = Bundle()
                extras.putString("brandLogo", item.brandLogo)
                extras.putString("brandId", item.brandId.toString())
                intent.putExtras(extras)
                startActivity(intent)
                //                makeText(applicationContext, item.brandName, LENGTH_SHORT).show()
            }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.arrowBack -> {
                finish()
            }
        }
    }

//    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        var item: BrandsCard = brandList!![position]
//        val intent = Intent(this, CardHolderActivity::class.java)
//        intent.putExtra("brandImage",item.brandLogo)
//        startActivity(intent)
//    }
}