package com.fpoly.assignemnt_gd1.ui.home

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assigment.MainApplication
import com.example.assigment.R
import com.example.assigment.UI.addAndEdit.AddAndEditProduct
import com.example.assigment.data.Repository.AppRepository
import com.example.assigment.data.source.remote.Api.Appfactory
import com.example.assigment.data.source.remote.AppRemoteDataSource
import com.example.assigment.goBackFragment
import com.example.assigment.snack
import com.fpoly.code4fun.ui.home.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var appRepository: AppRepository
    private lateinit var homeAdapter: HomeAdapter

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleData()
    }

    private fun handleData() {
       GlobalScope.launch(Dispatchers.IO) {
           val response=appRepository.getProducts().unwrap()
           withContext(Dispatchers.Main){
               homeAdapter.submitList(response.products)
           }
       }
    }
    private fun handleDelete(id:String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response=appRepository.deleteProduct(id)
            withContext(Dispatchers.Main){
                if(response.statusCode==200){
                    view?.snack("Delete successfully!")
                    handleData()
                }else{
                    view?.snack("ERROR:"+response.message)
                }
            }
        }
    }
    private fun initView() {
        homeRecyclerView.setHasFixedSize(true)
        homeRecyclerView.layoutManager = LinearLayoutManager(activity)
        homeAdapter= HomeAdapter(this).apply {
            onItemClick = {
                activity?.run {
                    supportFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.main, AddAndEditProduct.getInstance(it))
                        .addToBackStack(null)
                        .commit()
                }
            }
            onDelete={
                handleDelete(it)
            }
        }
        homeRecyclerView.adapter =homeAdapter
        homeFloatingActionButton.setOnClickListener {
            activity?.run {
                supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.main, AddAndEditProduct.getInstance(null))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}