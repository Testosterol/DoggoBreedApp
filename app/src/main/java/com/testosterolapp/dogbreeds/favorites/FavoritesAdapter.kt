package com.testosterolapp.dogbreeds.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.testosterolapp.dogbreeds.R
import com.testosterolapp.dogbreeds.data.BreedPicture
import com.testosterolapp.dogbreeds.database.DaoRepository
import com.testosterolapp.dogbreeds.util.GenericViewHolder
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

class FavoritesAdapter(mContext: Context) : PagedListAdapter<BreedPicture,
        GenericViewHolder>(DiffUtilCallBack()) {

    private val daoRepository: DaoRepository = DaoRepository(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val contactView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_favorites_item_layout, parent, false)
        return MyViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.onBindView(position)
    }

    inner class MyViewHolder(itemView: View) : GenericViewHolder(itemView) {
        private var dogImage: ImageView = itemView.findViewById(R.id.dog_imageview)
        private var favButton: Button = itemView.findViewById(R.id.favorite_button)

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindView(position: Int) {
            if (position <= -1) {
                return
            }
            try {
                if (getItem(position) != null) {
                    favButton.setOnClickListener {
                        daoRepository.updateFavoriteBreed(false, getItem(position)!!.id_breed_picture)
                        notifyItemRemoved(position)
                        notifyDataSetChanged()
                    }

                    Glide.with(itemView)
                        .load(getItem(position)!!.thumbnail)
                        .centerCrop()
                        .placeholder(R.drawable.ic_picture)
                        .into(dogImage)
                }
            }catch (e: Exception){
                Log.d("FavoritesAdapter", "e:$e")
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<BreedPicture>() {
        override fun areItemsTheSame(oldItem: BreedPicture, newItem: BreedPicture): Boolean {
            return oldItem.id_breed_picture == newItem.id_breed_picture
        }

        override fun areContentsTheSame(oldItem: BreedPicture, newItem: BreedPicture): Boolean {
            return oldItem.isFavorite == newItem.isFavorite
        }
    }

    //due 'androidx.paging:paging-runtime-ktx:2.1.2'
    override fun getItem(position: Int): BreedPicture? {
        try {
            return super.getItem(position)
        }catch (e: IndexOutOfBoundsException){
            Log.d("FavoritesAdapter", "e:$e")
        }
        return BreedPicture(0, "", false)
    }
}