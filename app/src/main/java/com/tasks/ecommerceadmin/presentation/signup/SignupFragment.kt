package com.tasks.ecommerceadmin.presentation.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceadmin.R
import com.tasks.ecommerceadmin.databinding.FragmentSigupBinding
import com.tasks.ecommerceadmin.common.CheckViewsValid
import com.tasks.ecommerceadmin.common.EmptyEditTextWatcher
import com.tasks.ecommerceadmin.common.Results
import com.tasks.ecommerceadmin.common.isEmail
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var _binding: FragmentSigupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignupViewModel by activityViewModels()
    private var email =""
    private var password =""
    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSigupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.email.addTextChangedListener(object: EmptyEditTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email=p0.toString()
                checkEmailorPasswordisValid(binding.email)
            }
        })
        binding.password.addTextChangedListener(object: EmptyEditTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password=p0.toString()
                checkEmailorPasswordisValid(binding.password)
            }
        })

        binding.signup.setOnClickListener {
            registerCustomer()
        }

        requireActivity().onBackPressedDispatcher.addCallback{
            findNavController().popBackStack(R.id.createAccountFragment,false)
        }
        binding.signIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        findNavController().navigate(R.id.action_SignupFragment_to_SigninFragment)
    }

    private fun checkEmailorPasswordisValid(editText:EditText) {
        checksViewValids.checkFocusedEdittext(editText)
        if(email.isBlank() || password.isBlank()|| !isEmail(email) || password.length<7){
            checksViewValids.notEnabled(binding.signup)
        }else{
            checksViewValids.setEnabled(binding.signup)
        }
    }
    private fun registerCustomer() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val firstName = viewModel.firstName
        val lastName = viewModel.lastName
        val userName = viewModel.userName
        viewModel.registerCustomer(email,password,firstName,lastName,userName)
        viewModel.registerResult.observe(viewLifecycleOwner){ result ->
            when (result) {
                is Results.Success -> {
                    val response = result.data
                    signIn()
               }
                is Results.Error -> {
                    Log.d("ResultError",result.exception)

                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}