package ge.lifecard.zcard.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import ge.lifecard.zcard.R
import ge.lifecard.zcard.models.BrandsCard
import kotlinx.android.synthetic.main.card_item.view.*

class BrandsCardAdapter(var context: Context, var list: List<BrandsCard>?) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return list!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount() = list!!.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.card_item, null)
        var imageResource: ImageView = view.brand_image_id
        var listItem: BrandsCard = list!![position]

        Glide.with(context)
            .load(listItem.brandLogo!!)
            .into(imageResource)

        return view
    }
}
