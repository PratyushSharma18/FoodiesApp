package com.pratyushvkp.foodies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.pratyushvkp.foodies.Models.RestaurantModel

class SuccessOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_order)


        val restaurantModel: RestaurantModel? = intent.getParcelableExtra("RestauranModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurantModel?.name)
        actionBar?.setSubtitle(restaurantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(false)

         val buttonDone = findViewById<TextView>(R.id.buttonDone)
        buttonDone.setOnClickListener{
            setResult(RESULT_OK)
       finish()
        }
    }
}