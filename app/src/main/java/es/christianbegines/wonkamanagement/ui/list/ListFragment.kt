package es.christianbegines.wonkamanagement.ui.list

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.christianbegines.wonkamanagement.R
import es.christianbegines.wonkamanagement.databinding.FragmentListBinding
import es.christianbegines.wonkamanagement.helpers.OompaLoompaAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.DividerItemDecoration
import es.christianbegines.wonkamanagement.models.FilterOompaLoompa
import es.christianbegines.wonkamanagement.models.OompaLoompa
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import android.view.MenuInflater
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.widget.Toolbar


@AndroidEntryPoint
class ListFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!
    private var getJob: Job? = null
    private lateinit var adapter: OompaLoompaAdapter
    private var filterByProffesion: Boolean = false
    private val viewModel: ListViewModel by viewModels()
    private val filterType = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        adapter = OompaLoompaAdapter { navigateToDetail(it) }
        binding.oompaloompaList.adapter = adapter
        binding.mainToolbar.setOnMenuItemClickListener(this)
        binding.radioFemale.setOnClickListener(this)
        binding.radioMale.setOnClickListener(this)
        binding.radioNone.setOnClickListener(this)
        setHasOptionsMenu(true)

        configureToolbar()

        getOompaLoompas(FilterOompaLoompa())
        return binding.root
    }

    private fun configureToolbar() {
        val searchIcon = binding.searchView.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val mSearchMenuItem = binding.mainToolbar.menu.findItem(R.id.search)
        val searchView = mSearchMenuItem.actionView as SearchView

        val searchEditText =
            searchView.findViewById<View>(R.id.search_src_text) as EditText
        searchEditText.setTextColor(resources.getColor(R.color.white))
        searchEditText.setHintTextColor(resources.getColor(R.color.white))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (filterByProffesion) {
                    if (newText != "")
                        getOompaLoompas(FilterOompaLoompa(null, null, newText.toString()))
                    else
                        getOompaLoompas(FilterOompaLoompa())
                } else {
                    if (newText == "") {
                        getOompaLoompas(FilterOompaLoompa())
                    } else {
                        getOompaLoompas(FilterOompaLoompa(newText.toString()))
                    }
                }
                return false
            }

        })
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
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_female ->
                    if (checked) {
                        getOompaLoompas(FilterOompaLoompa(null, "F", null))
                    }
                R.id.radio_male ->
                    if (checked) {
                        getOompaLoompas(FilterOompaLoompa(null, "M", null))
                    }
                R.id.radio_none ->
                    if (checked) {
                        getOompaLoompas(FilterOompaLoompa(null, null, null))
                    }
            }
        }
    }
}