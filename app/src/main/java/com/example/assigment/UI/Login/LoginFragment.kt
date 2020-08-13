package com.example.assigment.UI.Login

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.assigment.MainActivity
import com.example.assigment.R
import com.example.assigment.UI.Login.LoginFragment.Companion.newInstance
import com.example.assigment.UI.register.RegisterFragment
import com.example.assigment.data.source.remote.Api.Appfactory
import com.example.assigment.data.source.remote.AppRemoteDataSource
import com.fpoly.assignemnt_gd1.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.applyConnectionSpec
import com.example.assigment.data.Repository.AppRepository as AppRepository

class LoginFragment : Fragment() {
    private lateinit var appRepository: AppRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application=Application()
        appRepository =
            AppRepository.getInstance(AppRemoteDataSource.getInstance(Appfactory.instance,application))
        initView()
    }

    private fun initView() {
        textview_register.setOnClickListener {
            activity?.run {
                supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.main, RegisterFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        }
        btnLogin.setOnClickListener {
            if (validate() > 0) {
                handleData()
            }
        }
    }

    private fun validate(): Int {
        if (userEditext.text.isNullOrEmpty() || passwordEditext.text.isNullOrEmpty()) {
            Toast.makeText(activity, "do not empty", Toast.LENGTH_SHORT).show()
        } else return 1
        return 0
    }

     fun handleData() {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                appRepository.login(userEditext.text.toString(), passwordEditext.text.toString())
            withContext(Dispatchers.Main){
                Toast.makeText(activity,response.message,Toast.LENGTH_SHORT).show()
                    activity?.run {
                        supportFragmentManager
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.main, HomeFragment.newInstance())
                            .commit()
                    }
                }
            }
        }


    companion object {
        fun newInstance() = LoginFragment()
    }
}
