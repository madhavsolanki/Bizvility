package com.acculizein.zvility.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acculizein.zvility.databinding.ItemSavedListingViewholderBinding
import com.acculizein.zvility.models.client_models.SavedListing

class SavedListingAdapter(private val personList: List<SavedListing>) : RecyclerView.Adapter<SavedListingAdapter.SavedPersonViewHolder>() {

    inner class SavedPersonViewHolder(val binding: ItemSavedListingViewholderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPersonViewHolder {
        val binding = ItemSavedListingViewholderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedPersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedPersonViewHolder, position: Int) {
        val person = personList[position]
        holder.binding.tvSavedPersonName.text = person.name
        holder.binding.tvCategory.text = person.category
        holder.binding.ivSavedPersonImage.setImageResource(person.imageRes)
    }

    override fun getItemCount(): Int = personList.size
}
