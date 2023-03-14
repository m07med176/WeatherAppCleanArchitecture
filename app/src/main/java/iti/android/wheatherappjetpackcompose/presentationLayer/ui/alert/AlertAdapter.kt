package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.databinding.ItemAlertBinding
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel


class AlertAdapter(
    private val clickListener: ItemOnCLickListener,
) : ListAdapter<AlertModel, AlertAdapter.MyViewHolder>(AlertDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_alert, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binder(getItem(position), clickListener)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binder = ItemAlertBinding.bind(itemView)
        fun binder(data: AlertModel, itemOnCLickListener: ItemOnCLickListener) {
            binder.modelData = data
            binder.clickListener = itemOnCLickListener
            binder.executePendingBindings()
        }
    }


    class ItemOnCLickListener(
        val onDelete: (model: AlertModel) -> Unit,
    ) {
        fun onClick(model: AlertModel) = onDelete(model)
    }

    class AlertDiffCallback : DiffUtil.ItemCallback<AlertModel>() {
        override fun areItemsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean =
            oldItem.id == newItem.id &&
                    oldItem.endTime == newItem.endTime &&
                    oldItem.endDate == newItem.endDate &&
                    oldItem.endDate == newItem.endDate &&
                    oldItem.endTime == newItem.endTime &&
                    oldItem.latitude == newItem.latitude &&
                    oldItem.longitude == newItem.longitude &&
                    oldItem.city == newItem.city
    }
}

