package ge.lifecard.zcard.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import ge.lifecard.zcard.R


class LoadingDialog(context: Context) : Dialog(context) {
    private val mContext: Context = context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val inflateView: View = inflater.inflate(R.layout.loading_dialog,findViewById(R.id.loading_cont))
        setContentView(inflateView)
    }

}