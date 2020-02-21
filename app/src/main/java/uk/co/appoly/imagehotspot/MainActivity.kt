package uk.co.appoly.imagehotspot

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import uk.co.appoly.imagehotspot.databinding.ActivityMainBinding
import java.lang.Exception

const val HEAD = -19018034
const val UPPER_RIGHT_BODY = -18952498
const val UPPER_LEFT_BODY = -18886705
const val RIGHT_ARM = -18820912
const val LEFT_ARM = -18952498
const val LOWER_RIGHT_BODY = -18755119
const val LOWER_LEFT_BODY = -18754862
const val RIGHT_HAND = -18689326
const val LEFT_HAND = -18689069
const val RIGHT_LEG = -18623533
const val LEFT_LEG = -18623276
const val RIGHT_FOOT = -18557740
const val LEFT_FOOT = -18491947

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

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.body_colour_variation, options)
        mBinding.bodyImage.setImageBitmap(bitmap)

        setupImageTouchInterceptor()
    }

    private fun setupImageTouchInterceptor() {

        mBinding.bodyImage.setOnTouchListener { view, motionEvent ->

            Log.v("Debug", "Colour: ${bitmap.getPixel(motionEvent.x.toInt(), motionEvent.y.toInt())}")
            floodFill(motionEvent.x.toInt(), motionEvent.y.toInt())
            mBinding.bodyImage.setImageBitmap(bitmap)

            false
        }
    }

    private fun getSelectedBodyPart(colour: Int) {
        when(colour) {
            HEAD -> {
                Log.v("Debug", "HEAD")
                Toast.makeText(this, "HEAD", Toast.LENGTH_SHORT).show()
            }
            UPPER_RIGHT_BODY -> {
                Log.v("Debug", "UPPER_RIGHT_BODY")
                Toast.makeText(this, "UPPER_RIGHT_BODY", Toast.LENGTH_SHORT).show()
            }
            UPPER_LEFT_BODY -> {
                Log.v("Debug", "UPPER_LEFT_BODY")
                Toast.makeText(this, "UPPER_LEFT_BODY", Toast.LENGTH_SHORT).show()
            }
            RIGHT_ARM -> {
                Log.v("Debug", "RIGHT_ARM")
                Toast.makeText(this, "RIGHT_ARM", Toast.LENGTH_SHORT).show()
            }
            LEFT_ARM -> {
                Log.v("Debug", "LEFT_ARM")
                Toast.makeText(this, "LEFT_ARM", Toast.LENGTH_SHORT).show()
            }
            LOWER_RIGHT_BODY -> {
                Log.v("Debug", "LOWER_RIGHT_BODY")
                Toast.makeText(this, "LOWER_RIGHT_BODY", Toast.LENGTH_SHORT).show()
            }
            LOWER_LEFT_BODY -> {
                Log.v("Debug", "LOWER_LEFT_BODY")
                Toast.makeText(this, "LOWER_LEFT_BODY", Toast.LENGTH_SHORT).show()
            }
            RIGHT_HAND -> {
                Log.v("Debug", "RIGHT_HAND")
                Toast.makeText(this, "RIGHT_HAND", Toast.LENGTH_SHORT).show()
            }
            LEFT_HAND -> {
                Log.v("Debug", "LEFT_HAND")
                Toast.makeText(this, "LEFT_HAND", Toast.LENGTH_SHORT).show()
            }
            RIGHT_LEG -> {
                Log.v("Debug", "RIGHT_LEG")
                Toast.makeText(this, "RIGHT_LEG", Toast.LENGTH_SHORT).show()
            }
            LEFT_LEG -> {
                Log.v("Debug", "LEFT_LEG")
                Toast.makeText(this, "LEFT_LEG", Toast.LENGTH_SHORT).show()
            }
            RIGHT_FOOT -> {
                Log.v("Debug", "RIGHT_FOOT")
                Toast.makeText(this, "RIGHT_FOOT", Toast.LENGTH_SHORT).show()
            }
            LEFT_FOOT -> {
                Log.v("Debug", "LEFT_FOOT")
                Toast.makeText(this, "LEFT_FOOT", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun floodFill(initialX: Int, initialY: Int) {

        selectedColour = bitmap.getPixel(initialX,initialY)

        if (selectedColour == 0) return
        else if (initialColour == null) initialColour = selectedColour

        selectedColour?.let { getSelectedBodyPart(it) }

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
