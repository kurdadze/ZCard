package ge.lifecard.zcard.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ge.lifecard.zcard.R
import ge.lifecard.zcard.models.UserCards
import kotlinx.android.synthetic.main.user_card_item.view.*

class UserCardsAdapter(var context: Context, var list: List<UserCards>?) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount() = list!!.size

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.user_card_item, null)
        val brandImage: ImageView = view.brandImage
        val cardNumber: TextView = view.cardNumberText
        val cardHolder: TextView = view.cardHolderText
        val cardMoreInfo: TextView = view.cardMoreInfoText
        val listItem: UserCards = list!![position]

        cardNumber.text = listItem.cardNumber
        cardHolder.text = listItem.cardHolder
        cardMoreInfo.text = listItem.moreInfo
        Glide.with(context).load(listItem.brandLogo).into(brandImage)

        return view
    }
}