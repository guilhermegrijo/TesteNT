package com.br.guilherme.events.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.guilherme.domain.model.EventModel
import com.br.guilherme.events.R
import com.br.guilherme.events.databinding.EventFragmentBinding
import com.br.guilherme.events.model.EventItem
import com.br.guilherme.events.ui.components.EventAdapter
import com.br.guilherme.events.ui.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.net.UnknownHostException


@AndroidEntryPoint
class EventsFragment : Fragment(R.layout.event_fragment), ItemClickListener {

    private val viewModel: EventsViewModel by viewModels()

    private lateinit var _binding: EventFragmentBinding

    private val binding get() = _binding

    private val adapter = EventAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EventFragmentBinding.inflate(inflater, container, false)

        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listEvents.layoutManager = LinearLayoutManager(context)
        binding.listEvents.adapter = adapter

        binding.pullToRefresh.setOnRefreshListener {
            viewModel.loadEvents()
        }

        viewModel.data.observe(viewLifecycleOwner, { events ->

            if (events is ScreenState.Success<List<EventItem>>) {
                adapter.updateItems(events.data)
        }
        })


        viewModel.loadEvents()

    }

    override fun onItemClickListener(eventItem: EventItem) {
        val bundle = bundleOf("event_id" to eventItem.id)
        findNavController().navigate(R.id.action_eventsFragment_to_eventDetailFragment, bundle)
    }
}