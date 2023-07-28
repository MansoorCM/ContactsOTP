package com.example.contactsotp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.contactsotp.databinding.FragmentMessageBinding
import com.example.contactsotp.util.getOTP

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
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
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        new otp is generated everytime the fragment is opened. Adding a new button
        "Generate New OTP" will be a useful feature.
        */
        val otp = getOTP()
        binding.etOTPMessage.setText(getString(R.string.OTPMessage, otp))

        // AccountSID and AuthToken for Twilio should not be added directly in Android apps.
        // the recommended way is to add it as environment variable in backend and let the
        // android app request it from the backend.
        binding.buttonSendOTP.setOnClickListener {
            viewModel.sendMessage(
                getString(R.string.AccountSID),
                getString(R.string.AuthToken),
                getString(R.string.twilioPhoneNum),
                otp,
                binding.etOTPMessage.text.toString()
            )
        }
    }
}