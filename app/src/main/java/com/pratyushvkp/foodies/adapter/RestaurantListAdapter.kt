package com.pratyushvkp.foodies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pratyushvkp.foodies.Models.Hours
import com.pratyushvkp.foodies.Models.RestaurantModel
import com.pratyushvkp.foodies.R
import java.text.SimpleDateFormat
import java.util.*

class RestaurantListAdapter(val restaurantList: List<RestaurantModel?>? , val clickListener: RestaurantListClickListener):RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantListAdapter.MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_restaurant_list_row,parent,false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantList?.get(position))
        holder.itemView.setOnClickListener{
            clickListener.onItemClicked(restaurantList?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
       return restaurantList?.size!!
    }

    inner class MyViewHolder(view:View) :RecyclerView.ViewHolder(view){

        val thumbImage:ImageView = view.findViewById(R.id.thumbImage)
        val restaurantName:TextView = view.findViewById(R.id.restaurantName)
        val restaurantAddress:TextView = view.findViewById(R.id.restaurantAddress)
        val restaurantHours: TextView = view.findViewById(R.id.restaurantHours)

        fun bind(restaurantModel: RestaurantModel?){
            restaurantName.text = restaurantModel?.name
            restaurantAddress.text = "Address:" +restaurantModel?.address
            restaurantHours.text = "Today's Hours: " + getTodaysHours(restaurantModel?.hours!!)


            Glide.with(thumbImage).load(restaurantModel?.image).into(thumbImage)
        }

    }

    private fun getTodaysHours(hours: Hours): String?{
        val calendar: Calendar = Calendar.getInstance()
        val date: Date = calendar.time
        val day: String = SimpleDateFormat("EEEE",Locale.ENGLISH).format(date.time)
        return when(day){
            "Sunady" -> hours.Sunday
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday" -> hours.Wednesday
            "Thursday" -> hours.Thursday
            "Friday" -> hours.Friday
            "Saturday" -> hours.Saturday
            else -> hours.Sunday

        }    }


    interface RestaurantListClickListener {
        fun onItemClicked(restaurantModel: RestaurantModel?)
    }

}