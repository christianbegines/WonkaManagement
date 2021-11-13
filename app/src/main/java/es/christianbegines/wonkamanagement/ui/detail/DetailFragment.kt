package es.christianbegines.wonkamanagement.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.christianbegines.wonkamanagement.R
import es.christianbegines.wonkamanagement.databinding.FragmentDetailBinding
import es.christianbegines.wonkamanagement.databinding.FragmentListBinding

class DetailFragment : Fragment() {

    private var _binding :FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

}