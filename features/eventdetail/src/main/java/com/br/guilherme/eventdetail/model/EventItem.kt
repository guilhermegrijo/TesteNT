package com.br.guilherme.eventdetail.model

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import coil.load
import com.br.guilherme.domain.model.EventModel
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

data class EventItem(
     val people: List<Any?>,
     val date: Long,
     val description: String,
     val image: String,
     val longitude: Double,
     val latitude: Double,
     val price: String,
     val title: String,
     val id: String
) {
    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (url != null) {
                if (url.isNotEmpty()) {

                    view.load(url)
                }
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
    people,
    date,
    description,
    image,
    longitude,
    longitude,
    "Valor: R$${price.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)}",
    title,
    id
)