package com.example.contactsotp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsotp.adapter.ContactsListAdapter
import com.example.contactsotp.adapter.MessageListAdapter
import com.example.contactsotp.data.Contact
import com.example.contactsotp.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var contactsListAdapter: ContactsListAdapter
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messageListAdapter: MessageListAdapter

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            (requireActivity().application
                    as ContactsApplication).repository
        )
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
        messagesRecyclerView = binding.messagesRecyclerView

        contactsListAdapter = ContactsListAdapter(itemClicked = { contact ->
            onItemClick(contact)
        })
        contactsRecyclerView.adapter = contactsListAdapter

        messageListAdapter = MessageListAdapter()
        messagesRecyclerView.adapter = messageListAdapter

        // observe for changes in the contactsList livedata variable. if there
        // is any change, new list is submitted.
        viewModel.contactsList.observe(viewLifecycleOwner) { contactList ->
            contactsListAdapter.submitList(contactList)
        }

        // observe for changes in the messageItemList livedata variable. if there
        // is any change, new list is submitted.
        viewModel.messageItemList.observe(viewLifecycleOwner) { messageList ->
            messageListAdapter.submitList(messageList)
        }
    }

    private fun onItemClick(contact: Contact?) {
        contact?.let {
            viewModel.setCurrentContact(it)
            val action = ContactFragmentDirections.actionContactFragmentToContactInfoFragment()
            findNavController().navigate(action)
        }
    }
}