package com.br.guilherme.checkin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.br.guilherme.checkin.R
import com.br.guilherme.checkin.databinding.CheckinFragmentBinding
import com.br.guilherme.checkin.ui.util.CheckInListener
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.snackbar.Snackbar





@AndroidEntryPoint
class CheckInFragment : AppCompatDialogFragment(R.layout.checkin_fragment), CheckInListener {

    private val viewModel: CheckInViewModel by viewModels()

    private lateinit var _binding: CheckinFragmentBinding

    private val binding get() = _binding


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CheckinFragmentBinding.inflate(inflater, container, false)
        binding.listener = this
        binding.viewModel = viewModel
        _binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, {
            when(it){
                is ScreenState.Error -> {
                    showSnackbar(it.t.message)
                    findNavController().popBackStack()
                }
                is ScreenState.Success -> {
                    showSnackbar("Check-In efetuado")
                    findNavController().popBackStack()
                }
            }
        })

    }

    override fun registerCheckInClick() {
        arguments?.getString("event_id")?.let {
            viewModel.makeCheckIn(it)
        }


    }

    private fun showSnackbar(message: String?) {
        val parentLayout: View? = activity?.window?.decorView?.findViewById(android.R.id.content)
        if (parentLayout != null) {
            message?.let {
                Snackbar.make(parentLayout, it, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}