package com.example.contactsotp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.contactsotp.databinding.FragmentContactInfoBinding

class ContactInfoFragment : Fragment() {

    private lateinit var binding: FragmentContactInfoBinding
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
        binding = FragmentContactInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentContact?.let {
            // currentContact variable holds the data of the Contact previously selected.
            binding.etContactName.setText(
                getString(
                    R.string.contactName,
                    it.firstName,
                    it.lastName
                )
            )
            binding.etPhoneNum.setText(getString(R.string.contactPhoneNumber, it.phoneNum))
        }

    }

}