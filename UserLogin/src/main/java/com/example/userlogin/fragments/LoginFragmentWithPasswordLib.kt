package com.example.userlogin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.userlogin.R
import com.example.userlogin.databinding.FragmentLoginWithPasswordLibBinding
import com.example.userlogin.models.LoginResponseDTO
import com.example.userlogin.utils.Constants
import com.example.userlogin.utils.CustomProgressBar
import com.example.userlogin.utils.Utils
import com.example.userlogin.viewmodels.LoginViewModel

/**
 * A simple [Fragment] subclass.
 */
class LoginFragmentWithPasswordLib : Fragment() {
    lateinit var binding: FragmentLoginWithPasswordLibBinding
    lateinit var viewModel: LoginViewModel
    var progressBar = CustomProgressBar()

//        var emailAddress: String = "sagarmore53764@gmail.com"
    private var emailAddress: String = ""
    lateinit var loginResponseDTO: LoginResponseDTO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        emailAddress = arguments?.getString(Constants.EMAIL_ID).toString()

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login_with_password_lib,
            container,
            false
        )
        viewModel = Utils.obtainBaseObservable(
            activity as AppCompatActivity,
            LoginViewModel::class.java,
            this,
            binding.root
        )!!
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserver()

    }

    private fun viewModelObserver() {
        emailAddress.let {
            viewModel.loginIdEditTxt.value = it
        }

        viewModel.loginResponseDTOLiveData.observe(viewLifecycleOwner, Observer {
            loginResponseDTO = it
        })

        viewModel.navigateToForgotPassword.observe(viewLifecycleOwner, Observer {
            if (it) {
                val bundle = bundleOf(Constants.LOGIN_RESPONSE to loginResponseDTO)
                bundle.putString(Constants.EMAIL_ID, emailAddress)
                bundle.putBoolean(Constants.IS_FORGOT_PASS, true)
                binding.root.findNavController()
                    .navigate(R.id.action_loginFragmentWithPassword_to_forgotPassword, bundle)
            }
        })

        viewModel.closeClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                val bundle = Bundle()
                bundle.putString(Constants.EMAIL_ID, emailAddress)
                binding.root.findNavController()
                    .navigate(R.id.action_loginFragmentWithPassword_to_loginFragment, bundle)
            }
        })

        viewModel.loginIdEditTxt.observe(viewLifecycleOwner, Observer {
            viewModel.loginIdError.value = ""
        })

        viewModel.passwordEditTxt.observe(viewLifecycleOwner, Observer {
            viewModel.passwordError.value = ""
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer {
            if (it)
                progressBar.show(requireActivity(), "Please Wait...")
            else
                progressBar.dialog?.dismiss()
        })

        LoginViewModel.updateScreen.observe(viewLifecycleOwner, Observer {
            if (it) {

                val resultIntent = Intent()
                resultIntent.putExtra("accessToken", Constants.getAccessToken(requireActivity()))
                requireActivity().setResult(1, resultIntent)
                requireActivity().finish()
            }
        })

    }


    override fun onDestroy() {
        super.onDestroy()
        progressBar.dialog?.dismiss()
    }

}
