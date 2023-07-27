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
import com.google.android.material.tabs.TabLayout

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
            setUpMessageListVisibility()
            messageListAdapter.submitList(messageList)
        }

        // handles the tab layout clicks to navigate between contacts list and messages list.
        onTabChangedListener()
    }

    // if contacts list is not visible i.e. message list should be visible.
    // then if the message list is empty, show the placeholder text. else show the message list.
    // feels like this function could be improved.
    private fun setUpMessageListVisibility() {
        if (binding.contactsRecyclerView.visibility == View.GONE) {
            if (messageListAdapter.currentList.isEmpty()) {
                binding.tvMessagesPlaceholder.visibility = View.VISIBLE
                binding.messagesRecyclerView.visibility = View.GONE
            } else {
                binding.tvMessagesPlaceholder.visibility = View.GONE
                binding.messagesRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun onItemClick(contact: Contact?) {
        contact?.let {
            viewModel.setCurrentContact(it)
            val action = ContactFragmentDirections.actionContactFragmentToContactInfoFragment()
            findNavController().navigate(action)
        }
    }

    private fun onTabChangedListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    // when the contacts tab is clicked, contacts list is shown and
                    // the messages list is hidden
                    showContactsList()
                } else {
                    // when the messages tab is clicked, messages list is shown and
                    // the contacts list is hidden
                    showMessagesList()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun showContactsList() {
        binding.contactsRecyclerView.visibility = View.VISIBLE
        binding.messagesRecyclerView.visibility = View.GONE
        binding.tvMessagesPlaceholder.visibility = View.GONE
    }

    private fun showMessagesList() {
        binding.contactsRecyclerView.visibility = View.GONE

        // show message list if it is not empty, else show placeholder text.
        if (messageListAdapter.currentList.isEmpty()) {
            binding.messagesRecyclerView.visibility = View.GONE
            binding.tvMessagesPlaceholder.visibility = View.VISIBLE
        } else {
            binding.messagesRecyclerView.visibility = View.VISIBLE
            binding.tvMessagesPlaceholder.visibility = View.GONE
        }
    }
}