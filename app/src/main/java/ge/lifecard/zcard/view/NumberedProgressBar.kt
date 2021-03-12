package ge.lifecard.zcard.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.fonts.FontFamily
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat

import ge.lifecard.zcard.R

class NumberedProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ProgressBar(context, attrs, defStyleAttr) {

    private var textSize: Float = 0F
    private var font: Typeface? = null
    private var fontColor: Int = Color.GREEN

    private val paint = Paint()

    init {
        val attrArray = context.theme.obtainStyledAttributes(attrs,R.styleable.NumberedProgressBar,0, 0)
        try {
            textSize = attrArray.getDimensionPixelSize(R.styleable.NumberedProgressBar_npbTextSize,0).toFloat()
            val fontRes = attrArray.getResourceId(R.styleable.NumberedProgressBar_npbFontFamily,0)
            font = ResourcesCompat.getFont(context, fontRes)
            fontColor = attrArray.getColor(R.styleable.NumberedProgressBar_npbFontColor, Color.GREEN)
        } finally {
            attrArray.recycle()
        }

        progressDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.progress_bar, context.theme)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val canvaz = canvas ?: return
        paint.textAlign = Paint.Align.CENTER
        paint.color = fontColor
        paint.textSize = textSize
        if(font!=null)
            paint.typeface = font
        canvaz.drawText(
            progress.toString(),
            measuredWidth/2f,
            measuredHeight/2f - (paint.descent() + paint.ascent() /2),
            paint
        )
    }
}
