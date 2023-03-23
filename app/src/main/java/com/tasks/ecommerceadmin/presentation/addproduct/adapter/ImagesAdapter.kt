package com.tasks.ecommerceadmin.presentation.addproduct.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceadmin.databinding.ImagesBinding
import com.tasks.ecommerceadmin.presentation.addproduct.listener.OnItemClickListener

class ImagesAdapter(private var images: List<Uri>, private val listener: OnItemClickListener): RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesViewHolder {
          val inflater=LayoutInflater.from(parent.context)
          val binding=ImagesBinding.inflate(inflater,parent,false)
          return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        if (images.isNotEmpty() && position<=images.size-1){
            Glide.with(holder.itemView.context).load(images[position])
                .into(holder.binding.image)
        }

        holder.itemView.setOnClickListener {
               listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return if (images.isEmpty() || images.size==1 || images.size==2 ){
            4
        } else{
            images.size+2
        }


    }
    class ImagesViewHolder(val binding:ImagesBinding):RecyclerView.ViewHolder(binding.root) {}
}