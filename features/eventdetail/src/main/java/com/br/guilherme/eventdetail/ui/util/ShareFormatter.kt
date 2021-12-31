package com.br.guilherme.eventdetail.ui.util

import com.br.guilherme.eventdetail.model.EventItem
import java.text.SimpleDateFormat
import java.util.*

fun shareFormatter(eventItem: EventItem): String {

    val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm")
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = eventItem.date

    return "${eventItem.title} \n \n ${formatter.format(calendar.time)} \n \n ${eventItem.price}"

}