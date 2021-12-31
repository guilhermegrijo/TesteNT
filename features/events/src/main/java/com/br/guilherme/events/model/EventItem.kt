package com.br.guilherme.events.model

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.br.guilherme.domain.model.EventModel
import android.widget.TextView
import androidx.annotation.NonNull
import java.text.SimpleDateFormat
import java.util.*


data class EventItem(
    val date: Long,
    val description: String,
    val image: String,
    val price: Double,
    val title: String,
    val id: String
) {
    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String) {
            if (url.isNotEmpty()) {

                view.load(url)
            }
        }


        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        @BindingAdapter("dateFormat")
        fun bindDate(@NonNull textView: TextView?, date: Long?) {

            val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm")
            val calendar: Calendar = Calendar.getInstance()
            if (date != null && textView != null) {
                calendar.timeInMillis = date
                textView.text = formatter.format(calendar.time)
            }

        }
    }
}


fun EventModel.toView() = EventItem(
    date,
    description,
    image,
    price,
    title,
    id
)