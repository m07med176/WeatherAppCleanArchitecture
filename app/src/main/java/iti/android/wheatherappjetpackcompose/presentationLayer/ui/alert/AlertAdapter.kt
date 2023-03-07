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
        val clickListener: (model: AlertModel) -> Unit,
    ) {
        fun onClick(model: AlertModel) = clickListener(model)
    }

    class AlertDiffCallback : DiffUtil.ItemCallback<AlertModel>() {
        override fun areItemsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean =
            oldItem.id == newItem.id &&
                    oldItem.end == newItem.end &&
                    oldItem.start == newItem.start &&
                    oldItem.description == newItem.description &&
                    oldItem.event == newItem.event &&
                    oldItem.senderName == newItem.senderName &&
                    oldItem.tags == newItem.tags
    }
}

