package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Hourly
import iti.android.wheatherappjetpackcompose.databinding.ItemHourlyBinding


class HourlyAdapter(
    private val clickListener: ItemOnCLickListener,
) : ListAdapter<Hourly, HourlyAdapter.MyViewHolder>(HourlyDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_hourly, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binder(getItem(position), clickListener)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binder = ItemHourlyBinding.bind(itemView)
        fun binder(data: Hourly, itemOnCLickListener: ItemOnCLickListener) {
            binder.modelData = data
            binder.clickListener = itemOnCLickListener
            binder.executePendingBindings()
        }
    }


    class ItemOnCLickListener(
        val clickListener: (model: Hourly) -> Unit,
    ) {
        fun onClick(model: Hourly) = clickListener(model)
    }

    class HourlyDiffCallback : DiffUtil.ItemCallback<Hourly>() {
        override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean =
            oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean =
            oldItem.dt == newItem.dt &&
                    oldItem.clouds == newItem.clouds &&
                    oldItem.pop == newItem.pop &&
                    oldItem.dew_point == newItem.dew_point &&
                    oldItem.feels_like == newItem.feels_like &&
                    oldItem.humidity == newItem.humidity &&
                    oldItem.pressure == newItem.pressure &&
                    oldItem.visibility == newItem.visibility &&
                    oldItem.weather == newItem.weather &&
                    oldItem.temp == newItem.temp

    }
}

