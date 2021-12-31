package com.br.guilherme.events.ui.util

import android.widget.ImageView
import com.br.guilherme.events.model.EventItem

interface ItemClickListener {
    fun onItemClickListener(eventItem: EventItem)
}