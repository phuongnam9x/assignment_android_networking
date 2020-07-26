package com.example.assigment.UI.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.assigment.R
import com.example.assigment.UI.register.RegisterFragment
import com.example.assigment.data.source.remote.Api.Appfactory
import com.example.assigment.data.source.remote.AppRemoteDataSource
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
                appRepository =
                    AppRepository.getInstance(AppRemoteDataSource.getInstance(Appfactory.instance))
                    initView()
            }

    private fun initView() {
        textview_register.setOnClickListener { activity?.run { supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.main, RegisterFragment.newInstance())
            .addToBackStack(null)
            .commit()
        }
        }
    }
    fun handleData(){
        GlobalScope.launch(Dispatchers.IO){
            appRepository.login("","")
        }
    }
    companion object{
        fun newInstance() = LoginFragment()
    }
}
