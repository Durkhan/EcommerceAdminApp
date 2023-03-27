package com.tasks.ecommerceadmin.presentation.addproduct

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.tasks.ecommerceadmin.common.CheckViewsValid
import com.tasks.ecommerceadmin.common.EmptyEditTextWatcher
import com.tasks.ecommerceadmin.common.Results
import com.tasks.ecommerceadmin.common.initMediaManager
import com.tasks.ecommerceadmin.common.listener.UploadImageCallback
import com.tasks.ecommerceadmin.databinding.AddProductBinding
import com.tasks.ecommerceadmin.domain.UploadImageCloudinaryUseCase
import com.tasks.ecommerceadmin.presentation.addproduct.adapter.ImagesAdapter
import com.tasks.ecommerceadmin.presentation.addproduct.listener.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class AddProductFragment:Fragment(),OnItemClickListener {
    private var _binding: AddProductBinding?=null
    private val binding get()=_binding!!
    private var images= arrayListOf<Uri>()
    private var imagesDistincted= arrayListOf<Uri>()
    private var stringImagesUrls= arrayListOf<String>()
    private var pickImageLauncher: ActivityResultLauncher<String>? =null
    private val addProductViewModel: AddProductsViewModel by viewModels()

    private lateinit var adapter:ImagesAdapter

    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    private var product_title=""
    private var product_category=""
    private var product_brand=""
    private var product_color=""
    private var product_descrpition=""
    private var product_price=""

    @Inject
    lateinit var uploadImageCloudinaryUseCase: UploadImageCloudinaryUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=AddProductBinding.inflate(inflater,container,false)
        val layoutManager = LinearLayoutManager(context,HORIZONTAL,false)
        binding.recycleview.layoutManager = layoutManager
        adapter = ImagesAdapter(imagesDistincted,this)
        binding.recycleview.adapter = adapter
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEditText()
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imagesDistincted.clear()
                images.add(uri)
                imagesDistincted.addAll(images.distinct())
                setImagesForCloudinary(uri)
                adapter.notifyDataSetChanged()
            }
        }
        binding.mcontinue.setOnClickListener {
            addProduct()
        }

        context?.let { initMediaManager(it) }

        activity?.onBackPressedDispatcher?.addCallback {
            activity?.finishAffinity()
        }
    }


    private fun setImagesForCloudinary(uri: Uri) {
        uploadImageCloudinaryUseCase(uri,object : UploadImageCallback {
            override fun onUploadSuccess(url: String) {
                stringImagesUrls.add(url)
            }
        })
    }


    private fun addProduct() {
        val quantity= Random(70).nextInt()
        val imagesStringList=stringImagesUrls.distinct()

        lifecycleScope.launch{
                 addProductViewModel.addProduct(product_title,product_price.toDouble(),(product_price.toDouble().minus(50)),product_category,imagesStringList,quantity,product_color,"/${product_category}",product_brand,product_descrpition)
                 Log.d("ImagesList",""+imagesStringList)
        }

        addProductViewModel.addProductResult.observe(viewLifecycleOwner){ result ->
            when(result){
                is Results.Success ->{
                    addProductViewModel.sendNotification()
                    Toast.makeText(requireContext(),"Product added seccusfully",Toast.LENGTH_LONG).show()
                }
                is Results.Error ->{
                    Toast.makeText(requireContext(),result.exception,Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun setEditText() {
        with(binding){
            title.addTextChangedListener(object :EmptyEditTextWatcher(){
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                      product_title=text.toString()
                      checkEditTextNotBlank(title)
                }
            })
            category.addTextChangedListener(object :EmptyEditTextWatcher(){
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                      product_category=text.toString()
                      checkEditTextNotBlank(category)
                }
            })
            brand.addTextChangedListener(object :EmptyEditTextWatcher(){
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                      product_brand=text.toString()
                      checkEditTextNotBlank(brand)
                }
            })
            color.addTextChangedListener(object :EmptyEditTextWatcher(){
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                      product_color=text.toString()
                      checkEditTextNotBlank(color)
                }
            })
            desc.addTextChangedListener(object :EmptyEditTextWatcher(){
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                      product_descrpition=text.toString()
                      checkEditTextNotBlank(desc)
                }
            })
            price.addTextChangedListener(object :EmptyEditTextWatcher(){
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                      product_price=text.toString()
                      checkEditTextNotBlank(price)
                }
            })

        }
    }


    private fun checkEditTextNotBlank(editText: EditText) {
        checksViewValids.checkFocusedEdittext(editText)
        if(product_title.isBlank() || product_brand.isBlank()|| product_price.isBlank() ||
            product_color.isBlank() ||product_category.isBlank()
            || product_descrpition.isBlank() || images.isEmpty()
        ){
            checksViewValids.notEnabled(binding.mcontinue)
        }else{
            checksViewValids.setEnabled(binding.mcontinue)
        }
    }
    override fun onItemClick(position: Int) {
        selectImageFromGallery()
    }
    private fun selectImageFromGallery() {
        pickImageLauncher?.launch("image/*")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}