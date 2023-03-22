package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.databinding.ItemHourlyBinding
import iti.android.wheatherappjetpackcompose.domainLayer.models.HourlyModel


class HourlyAdapter : ListAdapter<HourlyModel, HourlyAdapter.MyViewHolder>(HourlyDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_hourly, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binder(getItem(position))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binder = ItemHourlyBinding.bind(itemView)
        fun binder(data: HourlyModel) {
            binder.modelData = data
            binder.executePendingBindings()
        }
    }


    class HourlyDiffCallback : DiffUtil.ItemCallback<HourlyModel>() {
        override fun areItemsTheSame(oldItem: HourlyModel, newItem: HourlyModel): Boolean =
            oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: HourlyModel, newItem: HourlyModel): Boolean =
            oldItem.dt == newItem.dt &&
                    oldItem.temp == newItem.temp &&
                    oldItem.image == newItem.image
    }
}

