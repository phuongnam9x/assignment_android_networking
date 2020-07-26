package com.example.assigment.UI.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assigment.R
import com.example.assigment.UI.Login.LoginFragment
import com.example.assigment.data.Model.Users
import com.example.assigment.data.Repository.AppRepository
import com.example.assigment.data.source.remote.Api.Appfactory
import com.example.assigment.data.source.remote.AppRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var appRepository: AppRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_register, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appRepository = AppRepository.getInstance(AppRemoteDataSource.getInstance(Appfactory.instance))
    }
    fun handleData(){
        GlobalScope.launch(Dispatchers.IO){
            appRepository.register(Users("","","",0))
        }
    }
    companion object{
        fun newInstance() = RegisterFragment()
    }
}