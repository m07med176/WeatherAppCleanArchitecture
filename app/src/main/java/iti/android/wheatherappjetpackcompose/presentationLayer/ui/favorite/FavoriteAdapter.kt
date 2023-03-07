package iti.android.wheatherappjetpackcompose.presentationLayer.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.databinding.ItemFavoriteBinding
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel


class FavoriteAdapter(
    private val clickListener: ItemOnCLickListener,
) : ListAdapter<FavPlacesModel, FavoriteAdapter.MyViewHolder>(FavPlaceDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binder(getItem(position), clickListener)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binder = ItemFavoriteBinding.bind(itemView)
        fun binder(data: FavPlacesModel, itemOnCLickListener: ItemOnCLickListener) {
            binder.modelData = data
            binder.clickListener = itemOnCLickListener
            binder.executePendingBindings()
        }
    }


    class ItemOnCLickListener(
        val clickListener: (model: FavPlacesModel) -> Unit,
    ) {
        fun onClick(model: FavPlacesModel) = clickListener(model)
    }

    class FavPlaceDiffCallback : DiffUtil.ItemCallback<FavPlacesModel>() {
        override fun areItemsTheSame(oldItem: FavPlacesModel, newItem: FavPlacesModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavPlacesModel, newItem: FavPlacesModel): Boolean =
            oldItem.id == newItem.id &&
                    oldItem.city == newItem.city &&
                    oldItem.location.latitude == newItem.location.latitude &&
                    oldItem.location.longitude == newItem.location.longitude
    }
}

