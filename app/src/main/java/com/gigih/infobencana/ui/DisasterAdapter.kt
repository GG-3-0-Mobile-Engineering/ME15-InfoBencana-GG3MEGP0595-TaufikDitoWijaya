package com.gigih.infobencana.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gigih.infobencana.data.DisasterReports
import com.gigih.infobencana.databinding.ListDisasterBinding

class DisasterAdapter(private var disasters: List<DisasterReports>) : RecyclerView.Adapter<DisasterAdapter.DisasterViewHolder>() {
    inner class DisasterViewHolder(val binding: ListDisasterBinding) : ViewHolder(binding.root)



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DisasterAdapter.DisasterViewHolder {
        val binding = ListDisasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DisasterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DisasterAdapter.DisasterViewHolder, position: Int) {
        with(holder) {
            val data = disasters[position]
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .apply(RequestOptions()).into(imageDisaster)
                tittleDisaster.text = data.disasterType
                descDisaster.text = data.text
            }
        }
    }

    override fun getItemCount(): Int {
        return disasters.size
    }

}