package com.tasks.ecommerceadmin.presentation.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceadmin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.tasks.ecommerceadmin.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment:Fragment() {
    private  var _binding: FragmentSplashBinding?=null
    private val binding get()=_binding!!

    private val viewModel:SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scaleAnimation = ScaleAnimation(
            1f, 40f,
            1f, 40f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 1000
        CoroutineScope(Dispatchers.Main).launch {
            binding.imageView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate))
            delay(800)
            binding.imageView.startAnimation(scaleAnimation)
            delay(700)
            try {
                if (!viewModel.getRememberUser() || viewModel.getToken().isBlank() || viewModel.isAccessTokenExpired())
                {
                    findNavController().navigate(R.id.action_splashFragment_to_SigninFragment)
                }
                else{
                    findNavController().navigate(R.id.action_splashFragment_to_addProductFrgament)
                }
            }catch (e:Exception){
                Log.d("Exception",e.message.toString())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}