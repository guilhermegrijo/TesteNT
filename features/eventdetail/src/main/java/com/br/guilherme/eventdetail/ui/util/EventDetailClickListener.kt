package com.br.guilherme.eventdetail.ui.util

import com.br.guilherme.eventdetail.model.EventItem

interface EventDetailClickListener {

    fun fabOnClick(eventItem: EventItem)

    fun shareOnClick()
}