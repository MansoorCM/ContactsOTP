package com.example.contactsotp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsotp.R
import com.example.contactsotp.data.MessageItem
import com.example.contactsotp.databinding.MessageListItemBinding
import com.example.contactsotp.util.getDate

class MessageListAdapter() :
    ListAdapter<MessageItem, MessageListAdapter.MessageListViewHolder>(DiffCallBack) {

    class MessageListViewHolder(
        val binding:
        MessageListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messageItem: MessageItem) {
            binding.tvContactName.text = messageItem.contactName
            binding.tvOtp.text = itemView.context.getString(R.string.OTPString, messageItem.otp)
            val date = getDate(messageItem.timeOfCreation)
            binding.tvTime.text = itemView.context.getString(R.string.timeOfCreation, date)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageListViewHolder {
        val viewHolder =
            MessageListViewHolder(
                MessageListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        viewHolder.itemView.setOnClickListener {
            // although there is no click functionality, click listener is added to get
            // touch feedback while clicking the list item.
        }

        return viewHolder
    }

    override fun onBindViewHolder(
        holder: MessageListViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    // using DiffUtil to efficiently handle item changes in the list.
    companion object DiffCallBack : DiffUtil.ItemCallback<MessageItem>() {
        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem == newItem
        }

    }
}