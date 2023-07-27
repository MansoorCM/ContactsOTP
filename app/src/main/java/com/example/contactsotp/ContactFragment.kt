package com.example.contactsotp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsotp.adapter.ContactsListAdapter
import com.example.contactsotp.data.Contact
import com.example.contactsotp.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var contactsListAdapter: ContactsListAdapter

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactsRecyclerView = binding.contactsRecyclerView

        contactsListAdapter = ContactsListAdapter(itemClicked = { contact ->
            onItemClick(contact)
        })
        contactsRecyclerView.adapter = contactsListAdapter
    }

    private fun onItemClick(contact: Contact?) {
        contact?.let {
        }
    }
}