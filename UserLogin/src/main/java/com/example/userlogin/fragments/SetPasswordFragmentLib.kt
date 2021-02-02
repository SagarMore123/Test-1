package com.example.userlogin.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.userlogin.R
import com.example.userlogin.databinding.FragmentSetPasswordLibBinding
import com.example.userlogin.utils.Constants
import com.example.userlogin.utils.CustomProgressBar
import com.example.userlogin.utils.Utils
import com.example.userlogin.viewmodels.SetPasswordViewModel

/**
 * A simple [Fragment] subclass.
 */
class SetPasswordFragmentLib : Fragment() {

    lateinit var binding: FragmentSetPasswordLibBinding
    lateinit var viewModel: SetPasswordViewModel
    var progressBar = CustomProgressBar()
    var emailAddress: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        emailAddress = arguments?.getString(Constants.EMAIL_ID).toString()

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_set_password_lib, container, false)
        viewModel = Utils.obtainBaseObservable(
            activity as AppCompatActivity,
            SetPasswordViewModel::class.java,
            this,
            binding.root
        )!!
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModelObserver()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailAddress.let {
            viewModel.loginId.value = it
        }

        /*proceedButton.setOnClickListener {
            findNavController().navigate(R.id.action_setPasswordFragment_to_loginFragmentWithPassword)
        }*/
    }

    private fun viewModelObserver() {

        viewModel.newPassword.observe(viewLifecycleOwner, Observer {
            viewModel.newPasswordError.value = ""
        })

        viewModel.confirmPassword.observe(viewLifecycleOwner, Observer {
            viewModel.confirmPasswordError.value = ""
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer {
            if (it)
                progressBar.show(activity as Activity, "Please Wait...")
            else
                progressBar.dialog?.dismiss()
        })

        viewModel.navigateToNextScreen.observe(viewLifecycleOwner, Observer {
            if (it) {
                val bundle = Bundle()
                bundle.putString(Constants.EMAIL_ID, emailAddress)
                binding.root.findNavController()
                    .navigate(R.id.action_setPasswordFragment_to_loginFragmentWithPassword, bundle)
            }
        })

        viewModel.closeClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                val bundle = Bundle()
                bundle.putString(Constants.EMAIL_ID, emailAddress)
                binding.root.findNavController()
                    .navigate(R.id.action_setPasswordFragment_to_loginFragment, bundle)
            }
        })
    }

}
