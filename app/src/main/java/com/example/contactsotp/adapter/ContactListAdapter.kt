package com.example.contactsotp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsotp.R
import com.example.contactsotp.data.Contact
import com.example.contactsotp.databinding.ContactListItemBinding

class ContactsListAdapter(
    private val itemClicked: (Contact?) -> Unit
) :
    ListAdapter<Contact, ContactsListAdapter.ContactsListViewHolder>(DiffCallBack) {

    class ContactsListViewHolder(
        val binding:
        ContactListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.tvContactName.text =
                itemView.context.getString(
                    R.string.contactName,
                    contact.firstName,
                    contact.lastName
                )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsListViewHolder {
        val viewHolder =
            ContactsListViewHolder(
                ContactListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        viewHolder.itemView.setOnClickListener {
            onClick(viewHolder)
        }

        return viewHolder
    }

    private fun onClick(viewHolder: ContactsListViewHolder) {
        val position = viewHolder.adapterPosition
        itemClicked(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ContactsListViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    // using DiffUtil to efficiently handle item changes in the list.
    companion object DiffCallBack : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }
}