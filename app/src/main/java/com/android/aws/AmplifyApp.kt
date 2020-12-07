package com.android.aws

import android.app.Application
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import timber.log.Timber

class AmplifyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        configureAmplify()
    }

    private fun configureAmplify() {
        try {
            Amplify.addPlugin(AWSApiPlugin()) // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)
            Timber.i("Initialized Amplify")
        } catch (error: AmplifyException) {
            Timber.e("Could not initialize Amplify")
        }
    }
}