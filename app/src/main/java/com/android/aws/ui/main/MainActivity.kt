package com.android.aws.ui.main

import android.app.Activity
import android.os.Bundle
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Credentials
import com.android.aws.R
import com.android.aws.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        registerObservers()
    }

    override fun onDestroy() {
        Amplify.DataStore.stop(
            {Timber.d("Stopped observing DataStore")},
            { error -> Timber.e(error) }
        )
        super.onDestroy()
    }

    private fun registerObservers() {
        observeCredentials()
    }

    private fun observeCredentials() {
        Amplify.DataStore.observe(Credentials::class.java,
            { Timber.d("Credentials | Observation began") },
            { /* Update recycler view adapter */ },
            { Timber.e("Credentials | Observation failed") },
            { Timber.d("Credentials | Observation complete") }
        )
    }
}