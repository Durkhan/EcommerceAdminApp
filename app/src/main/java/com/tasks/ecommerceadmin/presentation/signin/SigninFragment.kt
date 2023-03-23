package com.tasks.ecommerceadmin.presentation.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceadmin.R
import com.tasks.ecommerceadmin.databinding.FragmentSigninBinding
import com.tasks.ecommerceadmin.common.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SigninFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private var email =""
    private var password =""
    private var rememberMe = false
    private val signInViewModel: SignInViewModel by viewModels()
    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
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

        binding.signin.setOnClickListener {
            signIn()
        }
        binding.signup.setOnClickListener {
            signUp()
        }
        requireActivity().onBackPressedDispatcher.addCallback{
              activity?.finishAffinity()
        }
        checkRemember()

    }

    private fun checkRemember() {
        binding.rememberMe.setOnCheckedChangeListener { _, isChecked ->
            rememberMe = isChecked
        }
    }

    private fun signUp() {
        findNavController().navigate(R.id.action_SigninFragment_to_createAccountFragment)
    }

    private fun checkEmailorPasswordisValid(editText: EditText) {
        checksViewValids.checkFocusedEdittext(editText)
        if(email.isBlank() || password.isBlank()|| !isEmail(email) || password.length<7){
            checksViewValids.notEnabled(binding.signin)
        }else{
            checksViewValids.setEnabled(binding.signin)
        }
    }
    private fun signIn() {
        signInViewModel.signIn(email, password)
        signInViewModel.signInResult.observe(viewLifecycleOwner) { result ->
            when(result){
                is Results.Success -> {
                    signInViewModel.saveRememberMe(rememberMe)
                    findNavController().navigate(R.id.action_SigninFragment_to_addProductFrgament)
                }
                is Results.Error -> {
                    Toast.makeText(context,result.exception, Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}