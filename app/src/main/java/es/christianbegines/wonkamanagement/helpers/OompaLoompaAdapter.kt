package es.christianbegines.wonkamanagement.helpers

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import es.christianbegines.wonkamanagement.databinding.ListItemBinding
import es.christianbegines.wonkamanagement.models.OompaLoompa
import java.util.*
import kotlin.collections.ArrayList

class OompaLoompaAdapter(
    private val adapterAction: (OompaLoompa) -> Unit) : PagingDataAdapter<OompaLoompa,
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

    override fun onBindViewHolder(holder:  OompaLoompaViewHolder, position: Int) {
        val oompaLoompa = getItem(position)
        oompaLoompa?.let { holder.bind(it) }
    }

    class OompaLoompaViewHolder(
        private val adapterAction: (OompaLoompa) -> Unit,
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OompaLoompa) {
            binding.image.loadUrl(item.image)
            binding.firstName.text = item.firstName
            binding.secondName.text = item.lastName
            binding.age.text = item.age.toString()
            binding.profession.text = item.gender
            binding.itemLayout.setOnClickListener {
                adapterAction(item)
            }
        }
    }
}

private class  OompaLoompaDiffCallback : DiffUtil.ItemCallback<OompaLoompa>() {
    override fun areItemsTheSame(oldItem: OompaLoompa, newItem: OompaLoompa): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OompaLoompa, newItem: OompaLoompa): Boolean {
        return oldItem == newItem
    }
}