package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Daily
import iti.android.wheatherappjetpackcompose.databinding.ItemDailyBinding


class DailyAdapter(
    private val clickListener: ItemOnCLickListener,
) : ListAdapter<Daily, DailyAdapter.MyViewHolder>(DailyDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_daily, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binder(getItem(position), clickListener)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binder = ItemDailyBinding.bind(itemView)
        fun binder(data: Daily, itemOnCLickListener: ItemOnCLickListener) {
            binder.modelData = data
            binder.clickListener = itemOnCLickListener
            binder.executePendingBindings()
        }
    }


    class ItemOnCLickListener(
        val clickListener: (model: Daily) -> Unit,
    ) {
        fun onClick(model: Daily) = clickListener(model)
    }

    class DailyDiffCallback : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean =
            oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean =
            oldItem.dt == newItem.dt &&
                    oldItem.dew_point == newItem.dew_point &&
                    oldItem.clouds == newItem.clouds &&
                    oldItem.feels_like == newItem.feels_like &&
                    oldItem.humidity == newItem.humidity &&
                    oldItem.moon_phase == newItem.moon_phase

    }
}

