package com.rangga.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rangga.storyapp.R
import com.rangga.storyapp.data.response.ListStoryDataResponse
import com.rangga.storyapp.ui.DetailStoryActivity
import androidx.core.util.Pair

class ListStoryAdapter(private val listStory: List<ListStoryDataResponse>) :
    RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryDataResponse)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_home, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (id, name, description, photoUrl) = listStory[position]
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.imgPhoto)
        holder.tvName.text = name
        holder.tvDesc.text = description.take(50)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java)
            intent.putExtra(DetailStoryActivity.Id, id)
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.imgPhoto, "profile"),
                    Pair(holder.tvName, "name"),
                    Pair(holder.tvName, "description"),
                )
            holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
        }
    }

    override fun getItemCount(): Int = listStory.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_item_desc)
    }


}