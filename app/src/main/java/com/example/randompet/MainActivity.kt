package com.example.randompet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    var astronomyphotourl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val image = findViewById<ImageView>(R.id.imageView)
        val titleView = findViewById<TextView>(R.id.textView2)
        val dateView = findViewById<TextView>(R.id.textView3)
        getNextImage(button,image,titleView,dateView)
        Log.d("astronomyphotourl", "pet image URL set")
    }

    private fun getNextImage(button: Button, imageView: ImageView, titleView: TextView, dateView: TextView) {
        button.setOnClickListener {
            getnasaphoto { astronomyphotourl, astro_title, astro_date ->
                Log.d("AstronomyPhotoURL", astronomyphotourl)
                Glide.with(this)
                    .load(astronomyphotourl)
                    .override(100, 100)
                    .centerCrop()
                    .fitCenter()
                    .into(imageView)

                titleView.text = astro_title
                dateView.text = astro_date


            }
        }

    }



    private fun getnasaphoto(onSuccess: (String, String, String) -> Unit){
        val apikey = "dfI2HLc5Ualx533RaMAiwVJdTP4TT6pZlnLx1cGR"
        val client = AsyncHttpClient()



        client["https://api.nasa.gov/planetary/apod?api_key=$apikey", object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {

                // Access a JSON object response with `json.jsonObject`
                Log.d("DEBUG OBJECT", json.jsonObject.toString())
                val astronomyphotourl = json.jsonObject.getString("url")
                val astro_title = json.jsonObject.getString("title")
                val astro_date = json.jsonObject.getString("date")
                onSuccess(astronomyphotourl, astro_title, astro_date)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?
            ) {
                Log.e("API Request", "Failed with status code $statusCode")
            }
        }]
    }

}
