package es.christianbegines.wonkamanagement.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import es.christianbegines.wonkamanagement.R
import es.christianbegines.wonkamanagement.databinding.ListItemBinding
import es.christianbegines.wonkamanagement.helpers.loadUrl
import es.christianbegines.wonkamanagement.models.OompaLoompa

class OompaLoompaAdapter(
    private val adapterAction: (OompaLoompa) -> Unit
) : PagingDataAdapter<OompaLoompa,
        OompaLoompaAdapter.OompaLoompaViewHolder>(OompaLoompaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OompaLoompaViewHolder {
        return OompaLoompaViewHolder(
            adapterAction,
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: OompaLoompaViewHolder, position: Int) {
        val oompaLoompa = getItem(position)
        oompaLoompa?.let {
            holder.bind(it)
        }
    }

    class OompaLoompaViewHolder(
        val adapterAction: (OompaLoompa) -> Unit,
        val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OompaLoompa) {
            binding.image.loadUrl(item.image)
            binding.firstName.text = item.firstName
            binding.secondName.text = item.lastName
            binding.age.text = item.age.toString()
            binding.profession.text = item.profession
            binding.itemLayout.setOnClickListener {
                adapterAction(item)
            }
            when (item.gender) {
                "F" -> {
                    binding.genderIcon.setBackgroundResource(R.drawable.female)
                }
                "M" -> {
                    binding.genderIcon.setBackgroundResource(R.drawable.male)
                }
            }
        }
    }
}

private class OompaLoompaDiffCallback : DiffUtil.ItemCallback<OompaLoompa>() {
    override fun areItemsTheSame(oldItem: OompaLoompa, newItem: OompaLoompa): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OompaLoompa, newItem: OompaLoompa): Boolean {
        return oldItem == newItem
    }
}