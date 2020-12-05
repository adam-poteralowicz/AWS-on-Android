package com.android.aws

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import timber.log.Timber

class AmplifyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        configureAmplify()
    }

    private fun configureAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Timber.i("Initialized Amplify")
        } catch (error: AmplifyException) {
            Timber.e("Could not initialize Amplify")
        }
    }
}