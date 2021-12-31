package com.br.guilherme.eventdetail.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.br.guilherme.eventdetail.R
import com.br.guilherme.eventdetail.databinding.EventDetailFragmentBinding
import com.br.guilherme.eventdetail.model.EventItem
import com.br.guilherme.eventdetail.ui.util.EventDetailClickListener
import com.br.guilherme.eventdetail.ui.util.shareFormatter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EventDetailFragment : Fragment(R.layout.event_detail_fragment), EventDetailClickListener {


    private val viewModel: EventDetailViewModel by viewModels()

    private lateinit var _binding: EventDetailFragmentBinding

    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EventDetailFragmentBinding.inflate(inflater, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.viewModel = viewModel
        _binding.clickListener = this
        arguments?.getString("event_id")?.let { viewModel.loadEvent(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, { event ->

            when (event) {
                is ScreenState.Loading -> binding.shimmerViewContainer.startShimmer()
                is ScreenState.Success<EventItem> -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                }
                is ScreenState.Error -> {
                    Snackbar.make(view, "Error ao buscar informações", Snackbar.LENGTH_LONG)
                        .show()
                    findNavController().popBackStack()
                }
            }
        })
    }

    override fun fabOnClick(eventItem: EventItem) {
        val bundle = bundleOf("event_id" to eventItem.id)
        findNavController().navigate(R.id.action_eventDetailFragment_to_checkinFragment, bundle)
    }

    override fun shareOnClick() {
        val event = viewModel.state.value
        if (event is ScreenState.Success<EventItem>) {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareFormatter(event.data))
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    }
}