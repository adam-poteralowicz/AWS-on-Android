package com.android.aws.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amazonaws.mobile.client.*
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.android.aws.databinding.ActivityLoginBinding
import com.android.aws.ui.main.MainActivity
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPool: CognitoUserPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        setupUserPool()
        setupAwsAuth()
    }

    private fun setupUserPool() {
        userPool = CognitoUserPool(applicationContext, AWSConfiguration(applicationContext))
    }

    private fun setupAwsAuth() {
        AWSMobileClient.getInstance()
            .initialize(applicationContext, object : Callback<UserStateDetails?> {

                override fun onResult(userStateDetails: UserStateDetails?) {

                    if (userStateDetails != null) {
                        Timber.i(userStateDetails.userState.toString())
                        when (userStateDetails.userState) {
                            // after login success a JWT auth token is generated
                            UserState.SIGNED_IN -> {
                                val i = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(i)
                            }
                            UserState.SIGNED_OUT -> showSignIn()
                            else -> {
                                AWSMobileClient.getInstance().signOut()
                                showSignIn()
                            }
                        }
                    }
                }

                override fun onError(e: Exception) {
                    Timber.e(e)
                }
            })
    }

    private fun showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(
                this,
                SignInUIOptions.builder()
                    .nextActivity(MainActivity::class.java)
                    .build()
            )
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}