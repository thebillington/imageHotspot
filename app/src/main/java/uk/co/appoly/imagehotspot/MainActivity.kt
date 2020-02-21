package uk.co.appoly.imagehotspot

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import uk.co.appoly.imagehotspot.databinding.ActivityMainBinding
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var bitmap: Bitmap

    private var selectedColour: Int? = null
    private var initialColour: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val options = BitmapFactory.Options()
        options.inMutable = true

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.body_small, options)
        mBinding.bodyImage.setImageBitmap(bitmap)

        setupImageTouchInterceptor()
    }

    private fun setupImageTouchInterceptor() {

        mBinding.bodyImage.setOnTouchListener { view, motionEvent ->

            Log.v("Debug", "HERE1")

            floodFill(motionEvent.x.toInt(), motionEvent.y.toInt())
            mBinding.bodyImage.setImageBitmap(bitmap)

            Log.v("Debug", "HERE2")

            false
        }
    }

    private fun floodFill(initialX: Int, initialY: Int) {

        selectedColour = bitmap.getPixel(initialX,initialY)

        if (selectedColour == 0) return
        else if (initialColour == null) initialColour = selectedColour

        fillRow(initialX,initialY,1)
        fillRow(initialX,initialY-1,-1)

    }

    private fun fillRow(initialX: Int, y: Int, dy: Int) {
        var x = initialX
        if (bitmap.getPixel(x,y) != selectedColour) return

        while (bitmap.getPixel(x,y) == selectedColour) {
            x -= 1
        }
        x += 1

        while (bitmap.getPixel(x,y) == selectedColour) {
            fillRow(x, y+dy, dy)
            bitmap.setPixel(x,y,if (selectedColour == Color.GREEN) initialColour ?: Color.GREEN else Color.GREEN)
            x += 1
        }
    }

}
