package com.example.userlogin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.userlogin.R
import com.example.userlogin.databinding.FragmentLoginUserBinding
import com.example.userlogin.models.LoginResponseDTO
import com.example.userlogin.utils.Constants
import com.example.userlogin.utils.CustomProgressBar
import com.example.userlogin.utils.Utils
import com.example.userlogin.viewmodels.FirstTimeLoginViewModel

/**
 * A simple [Fragment] subclass.
 */
class LoginUserFragment : Fragment() {

    lateinit var binding: FragmentLoginUserBinding
    lateinit var viewModel: FirstTimeLoginViewModel
    var progressBar = CustomProgressBar()
    lateinit var loginResponseDTO: LoginResponseDTO
//    var emailAddress: String = ""
var emailAddress: String = "sagarmore53764@gmail.com"
//var emailAddress: String = "divyesh.tapase007@gmail.com"
//var emailAddress: String = "sagar.more@syspiretechnologies.com"s

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (arguments != null) {
            emailAddress = arguments?.getString(Constants.EMAIL_ID).toString()
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_user, container, false)
        viewModel = Utils.obtainBaseObservable(
            requireActivity(),
            FirstTimeLoginViewModel::class.java,
            this,
            binding.root
        )!!
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setEmail()
        viewModelObserver()

        return binding.root
    }

    private fun setEmail() {
        emailAddress.let {
            viewModel.loginId.value = emailAddress
        }
    }

    private fun viewModelObserver() {
        viewModel.loginId.observe(viewLifecycleOwner, Observer {
            viewModel.loginIdError.value = ""
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer {
            if (it)
                progressBar.show(requireActivity(), "Please Wait...")
            else
                progressBar.dialog?.dismiss()
        })

        viewModel.loginResponseDTOLiveData.observe(viewLifecycleOwner, Observer {
            loginResponseDTO = it
        })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                val bundle = Bundle()
                bundle.putString(Constants.EMAIL_ID, viewModel.loginId.value)
                binding.root.findNavController()
                    .navigate(R.id.action_loginFragment_to_loginFragmentWithPassword, bundle)
            } else {
                val bundle = bundleOf(Constants.LOGIN_RESPONSE to loginResponseDTO)
                bundle.putString(Constants.EMAIL_ID, viewModel.loginId.value)
                binding.root.findNavController()
                    .navigate(R.id.action_loginFragment_to_verifyOtpFragment, bundle)
            }
        })


    }

}
