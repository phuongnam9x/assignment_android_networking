package com.example.assigment.UI.register

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.assigment.MainApplication
import com.example.assigment.R
import com.example.assigment.UI.Login.LoginFragment
import com.example.assigment.data.model.Users
import com.example.assigment.data.Repository.AppRepository
import com.example.assigment.data.source.remote.Api.Appfactory
import com.example.assigment.data.source.remote.AppRemoteDataSource
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment(), View.OnClickListener {
    private lateinit var appRepository: AppRepository
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
        return inflater.inflate(R.layout.activity_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        btnRegister.setOnClickListener { if (validate() > 0) handleData() }

    }

    private fun handleData() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = appRepository.register(
                Users(
                    rg_full_name.text.toString(),
                    rg_user_name.text.toString(),
                    rg_pw1.text.toString(),
                    if (roleId.isChecked) 0 else 1
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, response.message, Toast.LENGTH_SHORT).show()
                activity?.run {
                    supportFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.main, LoginFragment.newInstance())
                        .commit()
                }
            }
        }
    }
    private fun validate(): Int {
        if (rg_full_name.text.isNullOrEmpty() || rg_user_name.text.isNullOrEmpty() || rg_pw1.text.isNullOrEmpty()) {
            Toast.makeText(activity, "do not empty", Toast.LENGTH_SHORT).show()
        } else return 1
        return 0
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }
}
