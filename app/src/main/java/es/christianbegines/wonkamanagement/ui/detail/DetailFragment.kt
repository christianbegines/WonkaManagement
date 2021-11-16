package es.christianbegines.wonkamanagement.ui.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.christianbegines.wonkamanagement.R
import es.christianbegines.wonkamanagement.databinding.FragmentDetailBinding
import es.christianbegines.wonkamanagement.helpers.loadUrl
import es.christianbegines.wonkamanagement.models.OompaLoompa
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.onEach {
            handleState(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getOompaLoompa(args.id)

    }

    private fun handleState(state: DetailViewModel.State) {
        when (state) {
            is DetailViewModel.State.Loading -> {
            }
            is DetailViewModel.State.Error -> handleError(state.message)
            is DetailViewModel.State.Success -> handleSuccess(
                state.oompaLoompa
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccess(oommpaLoompa: OompaLoompa) {
        binding.image.loadUrl(oommpaLoompa.image)
        binding.name.text = oommpaLoompa.firstName + " " + oommpaLoompa.lastName
        binding.profession.text = oommpaLoompa.profession
        binding.age.text = oommpaLoompa.age.toString()
        binding.description.text = oommpaLoompa.description
        when (oommpaLoompa.gender) {
            "F" -> {
                binding.genderImage.setBackgroundResource(R.drawable.female)
            }
            "M" -> {
                binding.genderImage.setBackgroundResource(R.drawable.male)
            }
        }
    }

    private fun handleError(message: Int) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    R.string.ok
                ) { dialog, _ ->
                    dialog.cancel()
                }
                setMessage(message)
                setTitle(R.string.unknownError)
            }
            builder.create()
            builder.show()
        }

    }

}