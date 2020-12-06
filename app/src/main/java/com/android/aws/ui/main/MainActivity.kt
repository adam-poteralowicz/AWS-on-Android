package com.android.aws.ui.main

import android.app.Activity
import android.os.Bundle
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.android.aws.R
import timber.log.Timber

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAmplify()
    }

    private fun setupAmplify() {
        try {
            Amplify.addPlugin(AWSApiPlugin()) // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)
            Timber.i("Initialized Amplify")
        } catch (e: AmplifyException) {
            Timber.e(e)
        }
    }
}