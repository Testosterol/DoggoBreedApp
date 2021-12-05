package com.testosterolapp.dogbreeds.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.testosterolapp.dogbreeds.R
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.util.GenericViewHolder

class MainActivityAdapter(private val onClickListener: MainActivityAdapterOnClickListener) :
    PagedListAdapter<Breed, GenericViewHolder>(DiffUtilCallBack()) {

    private var breed: Breed? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val contactView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_main_item_layout,
            parent, false
        )
        return MyViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.onBindView(position)
    }

    inner class MyViewHolder(itemView: View) : GenericViewHolder(itemView) {
        private var mName: TextView = itemView.findViewById(R.id.breed_name)

        override fun onBindView(position: Int) {
            if (position <= -1) {
                return
            }
            breed = try {
                getItem(position)
            } catch (e: IndexOutOfBoundsException) {
                return
            }
            if(breed != null) {
                mName.text = breed!!.breedName
            }
        }
        init {
            itemView.setOnClickListener { v: View? -> onClickListener.onBreedClickListener(getItem(adapterPosition), v, adapterPosition) }
        }
    }

    interface MainActivityAdapterOnClickListener {
        fun onBreedClickListener(breed: Breed?, v: View?, position: Int)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Breed>() {
        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.breedName == newItem.breedName
        }

    }

}