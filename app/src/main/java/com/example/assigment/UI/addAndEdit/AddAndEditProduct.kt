package com.example.assigment.UI.addAndEdit

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import com.example.assigment.MainApplication
import com.example.assigment.R
import com.example.assigment.data.Repository.AppRepository
import com.example.assigment.data.model.Product
import com.example.assigment.data.source.remote.Api.Appfactory
import com.example.assigment.data.source.remote.AppRemoteDataSource
import com.example.assigment.data.source.remote.response.BaseResponse
import com.example.assigment.dpToPx
import com.example.assigment.goBackFragment
import kotlinx.android.synthetic.main.add_and_edit_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddAndEditProduct : Fragment() {
    private lateinit var appRepository: AppRepository
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            appRepository =
                AppRepository.getInstance(AppRemoteDataSource.getInstance(Appfactory.instance,it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_and_edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
       bindView()
    }

    private fun bindView() {
        if (product == null) buttonUpdate.text =
            "Add" else buttonUpdate.text = "Update"
        nameProductText.setText(product?.title)
        deceptionProductText.setText(product?.deception)
        product?.price?.let { priceProductText.setText(it.toString()) }
        product?.imagePath
            ?.let {
                Glide.with(this)
                    .load("http://192.168.1.53:5000/public/image/" + it)
                    .placeholder(R.color.colorAccent)
                    .error(R.color.colorPrimary)
                    .dontAnimate()
                    .into(productImageView)
            }
            ?: when (val firstLetter = product?.title?.firstOrNull()) {
                null -> ColorDrawable(Color.parseColor("#fafafa"))
                else -> {
                    val size = requireContext()
                        .dpToPx(96)
                    TextDrawable
                        .builder()
                        .beginConfig()
                        .width(size)
                        .height(size)
                        .endConfig()
                        .buildRect(
                            firstLetter.toUpperCase().toString(),
                            ColorGenerator.MATERIAL.getColor(product?.deception)
                        )
                }
            }.let(productImageView::setImageDrawable)
    }

    private fun handleData(type: PostProductType) {
        if (type == PostProductType.ADD) {
            handleDataAdd()
        } else {
            handleDataEdit()
        }
    }

    private fun handleDataAdd() {
        if (validateAdd()) {
            val priceProductText =
                if (priceProductText.text.isNullOrEmpty()) null else priceProductText.text.toString()
                    .toDouble()
            GlobalScope.launch(Dispatchers.IO) {
                val response = appRepository.saveProduct(
                    nameProductText.text.toString(),
                    deceptionProductText.text.toString(),
                    priceProductText?.toDouble(),
                    imageUri
                )
                withContext(Dispatchers.Main) {
                    if (response.statusCode == 200) {
                        this@AddAndEditProduct.goBackFragment()
                    }
                    Toast.makeText(activity, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleDataEdit() {
        if (validateEdit()) {
            val priceProductText =
                if (priceProductText.text.isNullOrEmpty()) null else priceProductText.text.toString()
            GlobalScope.launch(Dispatchers.IO) {
                var response: BaseResponse<Any>? = null
                product?.id?.let {

                    response = appRepository.editProduct(
                        it,
                        nameProductText.text.toString(),
                        deceptionProductText.text.toString(),
                        priceProductText?.toDouble(),
                        imageUri
                    )
                }
                withContext(Dispatchers.Main) {
                    if (response?.statusCode == 200) {
                        this@AddAndEditProduct.goBackFragment()
                    }
                    Toast.makeText(activity, response?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView() {
        arguments?.getParcelable<Product>(ARGUMENT_PRODUCT)?.let {
            product = it
        }
        handelClick()
    }

    private fun handelClick() {
        lifecycleScope.launch {
            productImageView.setOnClickListener {
                val launcher =
                    registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
                        uri?.let {
                            Glide.with(this@AddAndEditProduct).load(it)
                                .centerCrop()
                                .dontAnimate()
                                .into(productImageView)
                            imageUri = it
                        }
                    }
                launcher.launch(arrayOf("image/*"))
            }
        }

        buttonUpdate.setOnClickListener {
            handleData(if (product == null) PostProductType.ADD else PostProductType.EDIT)
        }
    }

    private fun validateAdd(): Boolean {
        if (nameProductText.text.isNullOrEmpty()
            || priceProductText.text.isNullOrEmpty()
            || deceptionProductText.text.isNullOrEmpty()
            || imageUri == null
        ) {
            Toast.makeText(activity, "do not empty", Toast.LENGTH_SHORT).show()
        } else return true
        return false
    }

    private fun validateEdit(): Boolean {
        if (nameProductText.text.isNullOrEmpty()
            || priceProductText.text.isNullOrEmpty()
            || deceptionProductText.text.isNullOrEmpty()
        ) {
            Toast.makeText(activity, "do not empty", Toast.LENGTH_SHORT).show()
        } else return true
        return false
    }


    companion object {
        const val ARGUMENT_PRODUCT = "product"
        private var imageUri: Uri? = null


        fun getInstance(product: Product?) = AddAndEditProduct().apply {
            arguments = bundleOf(ARGUMENT_PRODUCT to product)
        }
    }
}