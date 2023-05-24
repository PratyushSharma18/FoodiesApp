package com.pratyushvkp.foodies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pratyushvkp.foodies.Models.Menus
import com.pratyushvkp.foodies.Models.RestaurantModel
import com.pratyushvkp.foodies.adapter.MenuListAdapter

class RestaurantMenuActivity : AppCompatActivity(), MenuListAdapter.MenuListClickListener {

    private var itemsInTheCartList: MutableList<Menus?>? = null
    private var totalItemInCart = 0
    private var menuList: List<Menus?>? = null
    private var menuListAdapter : MenuListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)

        val restaurantModel = intent?.getParcelableExtra<RestaurantModel>("RestaurantModel")
          val actionBar : ActionBar? = supportActionBar
        actionBar?.setTitle(restaurantModel?.name)
        actionBar?.setSubtitle(restaurantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuList = restaurantModel?.menus

        initRecyclerView(menuList)
        val checkOutButton = findViewById<TextView>(R.id.buttonCheckout)
        checkOutButton.setOnClickListener{
     if(itemsInTheCartList != null && itemsInTheCartList!!.size <= 0){
         Toast.makeText(this,"Please add some items in the cart",Toast.LENGTH_LONG).show()

     } else{
         restaurantModel?.menus =  itemsInTheCartList
         val intent = Intent(this,PlaceYourOrderActivity::class.java)
         intent.putExtra("RestaurantModel",restaurantModel)
         startActivityForResult(intent,1000)
     }
        }
    }

    private fun initRecyclerView(menus: List<Menus?>?) {
       val menuRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        menuRecyclerView.layoutManager = GridLayoutManager(this,2)
        menuListAdapter = MenuListAdapter(menus,this)
         menuRecyclerView.adapter = menuListAdapter
    }

    override fun addToCartClickListener(menu: Menus) {
       if(itemsInTheCartList == null){
           itemsInTheCartList = ArrayList()
       }
        itemsInTheCartList?.add(menu)
        totalItemInCart = 0

        for(menu in itemsInTheCartList!!){
            totalItemInCart= totalItemInCart + menu?.totalInCart!!
        }
        val checkOutButton = findViewById<TextView>(R.id.buttonCheckout)
        checkOutButton.text = "Checkout (" + totalItemInCart + ") Items"
    }


    override fun updateCartClickListener(menu: Menus) {
        val index = itemsInTheCartList!!.indexOf(menu)
        itemsInTheCartList?.removeAt(index)
        itemsInTheCartList?.add(menu)
        totalItemInCart = 0
        for(menu in itemsInTheCartList!!){
            totalItemInCart= totalItemInCart + menu?.totalInCart!!
        }
        val checkOutButton = findViewById<TextView>(R.id.buttonCheckout)
        checkOutButton.text = "Checkout (" + totalItemInCart + ") Items"
    }

    override fun removeFromCartClickListener(menu: Menus) {
        if(itemsInTheCartList!!.contains(menu)){
            itemsInTheCartList?.remove(menu)
            totalItemInCart = 0
            for(menu in itemsInTheCartList!!){
                totalItemInCart= totalItemInCart + menu?.totalInCart!!
            }
            val checkOutButton = findViewById<TextView>(R.id.buttonCheckout)
            checkOutButton.text = "Checkout (" + totalItemInCart + ") Items"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1000 && resultCode == RESULT_OK){
            finish()
        }
    }
}