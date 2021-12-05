package com.testosterolapp.dogbreeds.breeds

import android.content.Context
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

class BreedPicturesAdapter(mContext: Context) : PagedListAdapter<BreedPicture,
        GenericViewHolder>(DiffUtilCallBack()) {

    private var breedPicture: BreedPicture? = null
    private val daoRepository: DaoRepository = DaoRepository(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val contactView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_breed_pictures_item_layout,
            parent, false
        )
        return MyViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.onBindView(position)
    }

    inner class MyViewHolder(itemView: View) : GenericViewHolder(itemView) {
        var dogImage: ImageView? = itemView.findViewById(R.id.dog_imageview)
        var favButton: Button = itemView.findViewById(R.id.favorite_button)


        override fun onBindView(position: Int) {
            if (position <= -1) {
                return
            }
            breedPicture = try {
                getItem(position)
            } catch (e: IndexOutOfBoundsException) {
                return
            }
            if(breedPicture!!.isFavorite == true){
                favButton.setBackgroundResource(R.drawable.ic_favorite_red_24)
            }else{
                favButton.setBackgroundResource(R.drawable.ic_favorite_shadow_24)
            }


            favButton.setOnClickListener {
                if (getItem(position)!!.isFavorite == true) {
                    daoRepository.updateFavoriteBreed(false, getItem(position)!!.id_breed_picture)
                    favButton.setBackgroundResource(R.drawable.ic_favorite_shadow_24)

                } else {
                    daoRepository.updateFavoriteBreed(true, getItem(position)!!.id_breed_picture)
                    favButton.setBackgroundResource(R.drawable.ic_favorite_red_24)
                }
            }

            Glide.with(itemView)
                .load(breedPicture!!.thumbnail)
                .centerCrop()
                .placeholder(R.drawable.ic_picture)
                .into(dogImage!!)
        }
    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<BreedPicture>() {
        override fun areItemsTheSame(oldItem: BreedPicture, newItem: BreedPicture): Boolean {
            return oldItem.id_breed_picture == newItem.id_breed_picture
        }

        override fun areContentsTheSame(oldItem: BreedPicture, newItem: BreedPicture): Boolean {
            return oldItem.thumbnail == newItem.thumbnail
        }

    }
}
