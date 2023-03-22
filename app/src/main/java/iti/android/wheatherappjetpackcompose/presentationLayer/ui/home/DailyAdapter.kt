package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.databinding.ItemDailyBinding
import iti.android.wheatherappjetpackcompose.domainLayer.models.DailyModel


class DailyAdapter : ListAdapter<DailyModel, DailyAdapter.MyViewHolder>(DailyDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_daily, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binder(getItem(position))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binder = ItemDailyBinding.bind(itemView)
        fun binder(data: DailyModel) {
            binder.modelData = data
            binder.executePendingBindings()
        }
    }


    class DailyDiffCallback : DiffUtil.ItemCallback<DailyModel>() {
        override fun areItemsTheSame(oldItem: DailyModel, newItem: DailyModel): Boolean =
            oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: DailyModel, newItem: DailyModel): Boolean =
            oldItem.dt == newItem.dt &&
                    oldItem.desc == newItem.desc &&
                    oldItem.max == newItem.max &&
                    oldItem.min == newItem.min &&
                    oldItem.image == newItem.image


    }
}

