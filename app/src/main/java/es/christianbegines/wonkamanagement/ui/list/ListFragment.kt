package es.christianbegines.wonkamanagement.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import es.christianbegines.wonkamanagement.R
import es.christianbegines.wonkamanagement.databinding.FragmentListBinding
import es.christianbegines.wonkamanagement.helpers.OompaLoompaAdapter
import es.christianbegines.wonkamanagement.models.FilterOompaLoompa
import es.christianbegines.wonkamanagement.models.OompaLoompa
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!
    private var getJob: Job? = null
    private lateinit var adapter: OompaLoompaAdapter
    private val viewModel: ListViewModel by viewModels()
    private lateinit var filter: FilterOompaLoompa
    private var filterByProffesion = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            filter = FilterOompaLoompa()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.onEach {
            handleState(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.mainToolbar.setOnMenuItemClickListener(this)
        binding.radioFemale.setOnClickListener(this)
        binding.radioMale.setOnClickListener(this)
        binding.radioNone.setOnClickListener(this)
        binding.refresh.setOnClickListener(this)
        setHasOptionsMenu(true)

        configureToolbar()
        configureAdapter()

        getOompaLoompas(FilterOompaLoompa())
    }

    private fun configureAdapter() {
        adapter = OompaLoompaAdapter { navigateToDetail(it) }
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.refresh.visibility = View.VISIBLE
                    binding.oompaloompaList.visibility = View.GONE
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.oompaloompaList.visibility = View.VISIBLE
                    binding.refresh.visibility = View.GONE
                }
            }
        }
        binding.oompaloompaList.adapter = adapter
    }

    private fun configureToolbar() {
        val mSearchMenuItem = binding.mainToolbar.menu.findItem(R.id.search)
        val searchView = mSearchMenuItem.actionView as SearchView
        val searchEditText =
            searchView.findViewById<View>(R.id.search_src_text) as EditText
        searchEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        searchEditText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (newText == "") {
                        getOompaLoompas(query = FilterOompaLoompa())
                    } else {
                        if (filterByProffesion) {
                            filter.profession = newText
                        } else {
                            filter.name = newText
                        }
                        getOompaLoompas(filter)
                    }
                }
                return false
            }
        })
    }

    private fun handleState(state: ListViewModel.State) {
        when (state) {
            is ListViewModel.State.Loading -> {
            }
            is ListViewModel.State.Error -> handleError(state.message)
            is ListViewModel.State.Success -> {
            }
        }
    }

    private fun getOompaLoompas(query: FilterOompaLoompa) {
        getJob?.cancel()
        getJob = lifecycleScope.launch {
            viewModel.getOompaLoompas(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToDetail(item: OompaLoompa) {
        val action = ListFragmentDirections.fromListToDetail(item.id.toString())
        findNavController().navigate(action)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.gender -> {
                binding.filterLayout.visibility = View.VISIBLE
            }
            R.id.proffesion -> {
                filterByProffesion = true
                binding.mainToolbar.menu.findItem(R.id.search).expandActionView()
            }
            R.id.search -> {
                filterByProffesion = false
                binding.mainToolbar.menu.findItem(R.id.search).expandActionView()
            }
        }
        return true
    }


    override fun onClick(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_female ->
                    if (checked) {
                        filter.gender = FEMENINE
                        getOompaLoompas(filter)
                    }
                R.id.radio_male ->
                    if (checked) {
                        filter.gender = MASCULINE
                        getOompaLoompas(filter)
                    }
                R.id.radio_none ->
                    if (checked) {
                        filter.gender = null
                        getOompaLoompas(filter)
                        binding.filterLayout.visibility = View.GONE
                    }
            }
        } else {
            when (view.id) {
                R.id.refresh -> {
                    getOompaLoompas(FilterOompaLoompa())
                }
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

    companion object {
        private const val FEMENINE = "F"
        private const val MASCULINE = "M"
    }
}