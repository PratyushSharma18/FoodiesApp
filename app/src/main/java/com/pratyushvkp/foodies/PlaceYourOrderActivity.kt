package com.pratyushvkp.foodies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pratyushvkp.foodies.Models.RestaurantModel
import com.pratyushvkp.foodies.adapter.PlaceYourOrderAdapter
import java.awt.font.TextAttribute

class PlaceYourOrderActivity : AppCompatActivity() {
    var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    var isDeliveryOn : Boolean =  false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val restaurantModel: RestaurantModel? = intent.getParcelableExtra("RestaurantModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurantModel?.name)
        actionBar?.setSubtitle(restaurantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val buttonPlaceYourOrder = findViewById<TextView>(R.id.buttonPlaceYourOrder)
        buttonPlaceYourOrder.setOnClickListener{
            onPlaceOrderButtonClick(restaurantModel)
        }

        val switchDelivery = findViewById<SwitchCompat>(R.id.switchDelivery)
        switchDelivery?.setOnCheckedChangeListener { buttonView, isChecked ->

            val inputAddress = findViewById<EditText>(R.id.inputAddress)
            val inputCity = findViewById<EditText>(R.id.inputCity)
            val inputState = findViewById<EditText>(R.id.inputState)
            val inputZip = findViewById<EditText>(R.id.inputZip)
            val tvDeliveryCharge = findViewById<TextView>(R.id.tvDeliveryCharge)
            val tvDeliveryChargeAmount = findViewById<TextView>(R.id.tvDeliveryChargeAmount)
            if(isChecked){
               inputAddress.visibility = View.VISIBLE
                inputCity.visibility = View.VISIBLE
                inputState.visibility = View.VISIBLE
                inputZip.visibility = View.VISIBLE
                tvDeliveryCharge.visibility = View.VISIBLE
                tvDeliveryChargeAmount.visibility = View.VISIBLE
                isDeliveryOn = true
                calculateTotalAmount(restaurantModel)
            }else{
                inputAddress.visibility = View.GONE
                inputCity.visibility = View.GONE
                inputState.visibility = View.GONE
                inputZip.visibility = View.GONE
                tvDeliveryCharge.visibility = View.GONE
                tvDeliveryChargeAmount.visibility = View.GONE
                isDeliveryOn = false
                calculateTotalAmount(restaurantModel)
            }
        }

        initRecyclerView(restaurantModel)
        calculateTotalAmount(restaurantModel)
    }



    private fun initRecyclerView(restaurantModel:RestaurantModel?) {
        val cartItemRecyclerView = findViewById<RecyclerView>(R.id.cartItemsRecyclerView)
        cartItemRecyclerView.layoutManager = LinearLayoutManager(this)
    placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel?.menus)
        cartItemRecyclerView.adapter = placeYourOrderAdapter
    }

    private fun calculateTotalAmount(restaurantModel: RestaurantModel?){
        var subTotalAmount = 0f
        for(menu in restaurantModel?.menus!!){
            subTotalAmount += menu?.price!! * menu.totalInCart
        }

        val tvSubTotalAmount = findViewById<TextView>(R.id.tvSubtotalAmount)
        tvSubTotalAmount.text = "$" + String.format("%.2f",subTotalAmount)

        val tvDeliveryChargeAmount = findViewById<TextView>(R.id.tvDeliveryChargeAmount)
        if(isDeliveryOn){
            tvDeliveryChargeAmount.text = "$" + String.format("%.2f" , restaurantModel.delivery_charge?.toFloat())
        subTotalAmount += restaurantModel?.delivery_charge?.toFloat()!!
        }

        val tvTotalAmount = findViewById<TextView>(R.id.tvTotalAmount)
        tvTotalAmount.text = "$" + String.format("%.2f" , subTotalAmount)
    }

    private fun onPlaceOrderButtonClick(restaurantModel: RestaurantModel?) {
       val inputName = findViewById<EditText>(R.id.inputName)
        val inputAddress = findViewById<EditText>(R.id.inputAddress)
        val inputCity = findViewById<EditText>(R.id.inputCity)
        val inputState = findViewById<EditText>(R.id.inputState)
        val inputZip = findViewById<EditText>(R.id.inputZip)
        val  inputCardNumber = findViewById<EditText>(R.id.inputCardNumber)
        val inputCardExpiry = findViewById<EditText>(R.id.inputCardExpiry)
        val inputCardPin = findViewById<EditText>(R.id.inputCardPin)
    if(TextUtils.isEmpty(inputName.text.toString())){
        inputName.error = "Enter your name"
        return
    } else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())){
        inputAddress.error = "Enter your address"
        return
    } else if(isDeliveryOn && TextUtils.isEmpty(inputState.text.toString())){
        inputState.error = "Enter your city name"
        return
    }
    else if(isDeliveryOn && TextUtils.isEmpty(inputCity.text.toString())){
        inputCity.error = "Enter your city name"
        return
    } else if(isDeliveryOn && TextUtils.isEmpty(inputZip.text.toString())){
        inputZip.error = "Enter your Zip code"
        return
    } else if(TextUtils.isEmpty(inputCardNumber.text.toString())){
        inputCardNumber.error = "Enter your card number"
        return
    } else if(TextUtils.isEmpty(inputCardExpiry.text.toString())){
        inputCardExpiry.error = "Enter your card expiry"
        return
    } else if(TextUtils.isEmpty(inputCardPin.text.toString())){
        inputCardPin.error = "Enter your card pin/cvv"
        return
    }

        val intent  = Intent(this,SuccessOrderActivity::class.java)
        intent.putExtra("RestaurantModel" , restaurantModel)
        startActivityForResult(intent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if(requestCode==1000){
           setResult(RESULT_OK)
           finish()
       }

        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}